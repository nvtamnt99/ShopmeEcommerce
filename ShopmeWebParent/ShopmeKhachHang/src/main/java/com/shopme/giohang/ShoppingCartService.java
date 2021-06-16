package com.shopme.giohang;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.GiaVanChuyen;
import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.common.entity.SanPham;
import com.shopme.sanpham.SanPhamRepository;
import com.shopme.vanchuyen.VanchuyenReponsitory;

@Service
@Transactional
public class ShoppingCartService {
	
	static final int DIM_DIVISOR = 139;

	@Autowired private CartItemReponsitory cartRepo;
	
	@Autowired private SanPhamRepository sanPhamRepo;
	
	@Autowired private VanchuyenReponsitory vanChuyenRepo;
	
	public List<MatHangGioHang> listCartItem(KhachHang khachHang) {
		return cartRepo.findByKhachhang(khachHang);
	}
	
	public Integer addProduct(Integer maSanpham, Integer soLuong, KhachHang khachHang) {
		Integer addedQuantity = soLuong;
		
		SanPham sanPham = sanPhamRepo.findById(maSanpham).get();
		
		MatHangGioHang gioHang = cartRepo.findByKhachhangAndSanpham(khachHang, sanPham);
		
		if(gioHang != null) {
			addedQuantity = gioHang.getSoLuong() + soLuong;
			gioHang.setSoLuong(addedQuantity);
		} else {
			gioHang = new MatHangGioHang();
			gioHang.setSoLuong(soLuong);
			gioHang.setKhachhang(khachHang);
			gioHang.setSanpham(sanPham);
		}
		cartRepo.save(gioHang);
		return addedQuantity;
	}
	
	public float updateSoluong(Integer maSanpham, Integer soLuong, KhachHang khachHang) {
		
		cartRepo.updateSoluong(soLuong, maSanpham, khachHang.getMaKhachHang());
		SanPham sanPham = sanPhamRepo.findById(maSanpham).get();
		
		float subTotal = sanPham.getGiaBan() * soLuong;
		return subTotal;
	}
	
	public void removeSanpham(Integer maSanpham, KhachHang khachHang) {
		cartRepo.deleteByKhachhangAndSanpham(maSanpham, khachHang.getMaKhachHang());
	}
	
	public GiaVanChuyen getShippingRateForCustomer(KhachHang khachHang) {
		String tinh = khachHang.getTinh();
		System.out.println(tinh);
		if (tinh == null || tinh.isEmpty()) {
			tinh = khachHang.getThanhPho();
		}
		return vanChuyenRepo.findByDatNuocAndTinh(khachHang.getDatNuoc(), tinh);
	}
	public GiaVanChuyen getShippingRateForAddress(DiaChi diaChi) {
		String tinh = diaChi.getTinh();
		if (tinh == null || tinh.isEmpty()) {
			tinh = diaChi.getThanhPho();
		}
		return vanChuyenRepo.findByDatNuocAndTinh(diaChi.getDatNuoc(), tinh);
	}

	public float calculateProductCost(List<MatHangGioHang> cartItems) {
		float cost = 0.0f;
		
		for (MatHangGioHang item : cartItems) {
			cost += item.getSoLuong() * item.getSanpham().getGiaBan();
		}
		
		return cost;
	}
	
	public float calculateProductTotal(List<MatHangGioHang> cartItems) {
		float total = 0.0f;
		
		for (MatHangGioHang item : cartItems) {
			total += item.getTongTien();
		}
		
		return total;
	}
	
	public float calculateShippingCost(List<MatHangGioHang> cartItems, GiaVanChuyen rate) {
		float shippingCost = 0.0f;
		
		for (MatHangGioHang item : cartItems) {
			SanPham product = item.getSanpham();
			float dimWeight = (product.getCanNang() * product.getChieuRong() * product.getChieuCao()) / DIM_DIVISOR;
			float finalWeight = product.getChieuRong() > dimWeight ? product.getChieuRong() : dimWeight;
			float cost = finalWeight * rate.getGia() * item.getSoLuong();
			
			item.setShip(cost);
			
			shippingCost += cost;
		}		
		
		return shippingCost;
	}
}
