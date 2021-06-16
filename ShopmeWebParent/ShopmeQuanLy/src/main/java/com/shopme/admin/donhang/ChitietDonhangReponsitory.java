package com.shopme.admin.donhang;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopme.common.entity.ChiTietDonHang;

public interface ChitietDonhangReponsitory extends JpaRepository<ChiTietDonHang, Integer> {
	
	@Query("SELECT NEW com.shopme.common.entity.ChiTietDonHang(od.sanpham.nhanhieu.ten, od.tongphu, od.chiPhi, od.ship, od.soLuong)"
			+ " FROM ChiTietDonHang od WHERE od.donhang.thoiGianDatHang BETWEEN ?1 and ?2")
	public List<ChiTietDonHang> findByChiTietDonHangTenNhanHieuTimeBetween(Date startTime, Date endTime);
	
	@Query("SELECT NEW com.shopme.common.entity.ChiTietDonHang(od.tongphu, od.chiPhi, od.ship, od.sanpham.ten, od.soLuong)"
			+ " FROM ChiTietDonHang od WHERE od.donhang.thoiGianDatHang BETWEEN ?1 and ?2")
	public List<ChiTietDonHang> findByChiTietDonHangTenSanphamTimeBetween(Date startTime, Date endTime);	
}
