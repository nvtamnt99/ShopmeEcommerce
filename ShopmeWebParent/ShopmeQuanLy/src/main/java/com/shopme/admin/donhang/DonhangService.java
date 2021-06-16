package com.shopme.admin.donhang;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.KhachHang;

@Service
public class DonhangService {

	public static final int ORDERS_PER_PAGE = 10;
	
	@Autowired
	private DonhangReponsitory repo;
	
	public List<DonHang> listAll() {
		return repo.findAll(Sort.by("ho").ascending());
	}
	
	public Page<DonHang> listAll(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		
		return repo.findAll(pageable);
	}	
	
	public DonHang get(Integer id) throws DonhangNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new DonhangNotFoundException("Không tìm thấy đơn hàng có mã " + id);
		}
	}
	
	public void delete(Integer id) throws DonhangNotFoundException {
		Long count = repo.countBymaDonHang(id);
		if (count == null || count == 0) {
			throw new DonhangNotFoundException("Không tìm thấy đơn hàng có mã " + id); 
		}
		
		repo.deleteById(id);
	}
	
	public void save(DonHang orderInForm) {
		DonHang orderInDB = repo.findById(orderInForm.getMaDonHang()).get();
		updateOrderOverviewInfo(orderInForm, orderInDB);
		updateOrderShippingInfo(orderInForm, orderInDB);
		updateOrderProducts(orderInForm, orderInDB);
		updateOrderTracks(orderInForm, orderInDB);
		
		repo.save(orderInDB);
	}
	
	private void updateOrderOverviewInfo(DonHang orderInForm, DonHang orderInDB) {
		orderInDB.setTongPhu(orderInForm.getTongPhu());
		orderInDB.setGiaVanChuyen(orderInForm.getGiaVanChuyen());
		orderInDB.setThue(orderInForm.getThue());
		orderInDB.setTong(orderInForm.getTong());
		orderInDB.setChiPhi(orderInForm.getChiPhi());
		orderInDB.setPhuongThucThanhToan(orderInForm.getPhuongThucThanhToan());
		orderInDB.setTinhTrangDH(orderInForm.getTinhTrangDH());
	}
	
	private void updateOrderShippingInfo(DonHang orderInForm, DonHang orderInDB) {
		orderInDB.setGiaoNgay(orderInForm.getGiaoNgay());
		orderInDB.setNgayGiaoHang(orderInForm.getNgayGiaoHang());
		orderInDB.setHo(orderInForm.getHo());
		orderInDB.setTen(orderInForm.getTen());
		orderInDB.setSoDienThoai(orderInForm.getSoDienThoai());
		orderInDB.setDiaChi1(orderInForm.getDiaChi1());
		orderInDB.setDiaChi2(orderInForm.getDiaChi2());
		orderInDB.setThanhPho(orderInForm.getThanhPho());
		orderInDB.setTinh(orderInForm.getTinh());
		orderInDB.setQuocGia(orderInForm.getQuocGia());
		orderInDB.setMaBuuDien(orderInForm.getMaBuuDien());
	}
	
	private void updateOrderProducts(DonHang orderInForm, DonHang orderInDB) {
		orderInDB.getChiTietDH().clear();
		orderInDB.getChiTietDH().addAll(orderInForm.getChiTietDH());
	}
	
	private void updateOrderTracks(DonHang orderInForm, DonHang orderInDB) {
		orderInDB.getTheoDoiDH().clear();
		orderInDB.getTheoDoiDH().addAll(orderInForm.getTheoDoiDH());
	}
}
