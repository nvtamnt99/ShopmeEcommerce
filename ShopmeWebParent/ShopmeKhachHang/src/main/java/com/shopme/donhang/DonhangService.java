package com.shopme.donhang;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.ChiTietDonHang;
import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.common.entity.PhuongThucThanhToan;
import com.shopme.common.entity.SanPham;
import com.shopme.common.entity.TheoDoiDonHang;
import com.shopme.common.entity.TinhTrangDonHang;
import com.shopme.giohang.CartItemReponsitory;

@Service
@Transactional
public class DonhangService  {
	public static final int ORDERS_PER_PAGE = 4;

	@Autowired private DonhangReponsitory donHangRepo;
	
	@Autowired private CartItemReponsitory cartRepo;
	
	
	public DonHang datHang(KhachHang khachHang, DiaChi diaChi, List<MatHangGioHang> dsGiohang,
			PhuongThucThanhToan phuongThucThanhToan, float tongSanpham, float giaSanpham, float phiVanChuyen, float tongThanhtoan,
			int ngayVanchuyen) {
		
		DonHang donHang = new DonHang();
		donHang.setKhachHang(khachHang);
		donHang.setChiPhi(giaSanpham);
		donHang.setTongPhu(tongSanpham);
		donHang.setGiaVanChuyen(phiVanChuyen);
		donHang.setThue(0.0f);
		donHang.setTong(tongThanhtoan);
		donHang.setThoiGianDatHang(new Date());
		donHang.setTinhTrangDH(TinhTrangDonHang.NEW);
		donHang.setPhuongThucThanhToan(phuongThucThanhToan);
		donHang.setGiaoNgay(ngayVanchuyen);
		
		Date ngayGiao = calculateDeliverDate(ngayVanchuyen);
		
		donHang.setNgayGiaoHang(ngayGiao);
		
		if (diaChi == null) {
			donHang.copyShippingAddressFromCustomer(khachHang);
		}
		else {
			donHang.copyShippingAddress(diaChi);
		}
		
		Set<ChiTietDonHang> dschiTiet = donHang.getChiTietDH();
		
		for(MatHangGioHang matHangGioHang : dsGiohang) {
			SanPham sanPham = matHangGioHang.getSanpham();
			ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
			chiTietDonHang.setDonhang(donHang);
			chiTietDonHang.setSanpham(sanPham);
			chiTietDonHang.setSoLuong(matHangGioHang.getSoLuong());
			chiTietDonHang.setDonGia(sanPham.getGiaBan());
			chiTietDonHang.setChiPhi(sanPham.getChiPhi()* matHangGioHang.getSoLuong());
			chiTietDonHang.setTongphu(matHangGioHang.getTongTien());
			chiTietDonHang.setShip(matHangGioHang.getShip());
			
			dschiTiet.add(chiTietDonHang);
		}
		
		TheoDoiDonHang firstDonhang = new TheoDoiDonHang();
		firstDonhang.setDonhang(donHang);
		firstDonhang.setTinhTrangDonHang(TinhTrangDonHang.NEW);
		firstDonhang.setThoigian_capnhat(new Date());
		firstDonhang.setChuThich(TinhTrangDonHang.NEW.getDescription());
		
		donHang.getTheoDoiDH().add(firstDonhang);
		
		if (phuongThucThanhToan.equals(PhuongThucThanhToan.PAYPAL)) {
			TheoDoiDonHang theoDoiDonHang = new TheoDoiDonHang();
			theoDoiDonHang.setDonhang(donHang);
			theoDoiDonHang.setTinhTrangDonHang(TinhTrangDonHang.THANH_TOAN);
			theoDoiDonHang.setThoigian_capnhat(new Date());
			theoDoiDonHang.setChuThich(TinhTrangDonHang.THANH_TOAN.getDescription());
			donHang.getTheoDoiDH().add(theoDoiDonHang);
		}
		
		DonHang saveDonhang = donHangRepo.save(donHang);
		cartRepo.deleteByKhachhang(khachHang.getMaKhachHang());
		return saveDonhang;
		
	}
	
	public Date calculateDeliverDate(int deliverDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, deliverDays);
		
		return calendar.getTime();
	}
	public Page<DonHang> getOrdersForCustomer(KhachHang customer, int pageNum, 
			String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return donHangRepo.findAll(keyword, customer.getMaKhachHang(), pageable);
		}
		
		return donHangRepo.findAll(customer.getMaKhachHang(), pageable);
		
	}
	public DonHang getOrderDetails(Integer id, KhachHang customer) {
		return donHangRepo.findBymaDonHangAndKhachHang(id, customer);
	}
	public void delete(Integer id) throws DonhangNotFoundException {
		Long count = donHangRepo.countBymaDonHang(id);
		if (count == null || count == 0) {
			throw new DonhangNotFoundException("Không thể tìm thấy bất kỳ đơn đặt hàng nào có ID " + id); 
		}
		
		donHangRepo.deleteById(id);
	}
	public DonHang saveDonhang(DonHang donHang) {
		DonHang donHangupdate = donHangRepo.save(donHang);
		return donHangupdate;
	}
}
