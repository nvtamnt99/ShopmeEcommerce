package com.shopme.giohang;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.common.entity.SanPham;

@Repository
public interface CartItemReponsitory extends JpaRepository<MatHangGioHang, Integer> {

	public List<MatHangGioHang> findByKhachhang(KhachHang khachHang);

	public MatHangGioHang findByKhachhangAndSanpham(KhachHang khachHang, SanPham sanPham);
	
	@Query("UPDATE MatHangGioHang m set m.soLuong = ?1 WHERE m.sanpham.id = ?2 "
			+ "AND m.khachhang.id = ?3")
	@Modifying
	public void updateSoluong(Integer soLuong, Integer maSanpham, Integer maKhachHang);
	
	@Query("DELETE FROM MatHangGioHang m WHERE m.sanpham.id = ?1 AND m.khachhang.id = ?2")
	@Modifying
	public void deleteByKhachhangAndSanpham(Integer maSanpham, Integer maKhachHang);
	
	@Modifying
	@Query("DELETE MatHangGioHang m WHERE m.khachhang.id = ?1")
	public void deleteByKhachhang(Integer maKhachHang);
}
