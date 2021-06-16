package com.shopme.admin.khachhang;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopme.admin.phantrang.PhanTrangRepository;
import com.shopme.common.entity.KhachHang;

public interface KhachhangReponsitory extends PhanTrangRepository<KhachHang, Integer> {

	@Query("SELECT c FROM KhachHang c WHERE CONCAT(c.email, ' ', c.ho, ' ', c.ten, ' ', "
			+ "c.diaChi1, ' ', c.diaChi2, ' ', c.thanhPho, ' ', c.tinh, "
			+ "' ', c.maBuuDien, ' ', c.datNuoc.ten) LIKE %?1%")
	public Page<KhachHang> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE KhachHang c SET c.trangThai = ?2 WHERE c.maKhachHang = ?1")
	@Modifying
	public void updateTrangThai(@Param("id")Integer id, @Param("enabled") boolean enabled);
	
	@Query("SELECT c FROM KhachHang c WHERE c.email = ?1")
	public KhachHang findByEmail(String email);
	
	public List<KhachHang> findAll(Sort sort);
	
	public Long countBymaKhachHang(Integer id);
}
