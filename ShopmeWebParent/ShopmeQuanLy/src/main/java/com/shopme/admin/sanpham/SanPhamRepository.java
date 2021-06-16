package com.shopme.admin.sanpham;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.SanPham;

public interface SanPhamRepository extends PagingAndSortingRepository<SanPham, Integer> {
	
	public SanPham findByTen(String ten);
	
	@Query("UPDATE SanPham p SET p.trangThai = ?2 WHERE p.maSanPham = ?1")
	@Modifying
	public void updateEnabledStatus(Integer maSanPham, boolean trangThai);	
	
	public Long countByMaSanPham(Integer maSanPham);
	
	@Query("SELECT p FROM SanPham p WHERE p.ten LIKE %?1% " 
			+ "OR p.moTaNgan LIKE %?1% "
			+ "OR p.moTaDayDu LIKE %?1% "
			+ "OR p.nhanhieu.ten LIKE %?1% "
			+ "OR p.danhmuc.ten LIKE %?1%")
	public Page<SanPham> findAll(String keyword, Pageable pageable);
	
	@Query("SELECT p FROM SanPham p WHERE p.danhmuc.maDanhMuc = ?1 "
			+ "OR p.danhmuc.tatCaMaDanhMucCha LIKE %?2%")	
	public Page<SanPham> findAllInCategory(Integer categoryId, String categoryIdMatch, 
			Pageable pageable);

	@Query("SELECT p FROM SanPham p WHERE (p.danhmuc.maDanhMuc = ?1 "
			+ "OR p.danhmuc.tatCaMaDanhMucCha LIKE %?2%) AND "
			+ "(p.ten LIKE %?3% " 
			+ "OR p.moTaNgan LIKE %?3% "
			+ "OR p.moTaDayDu LIKE %?3% "
			+ "OR p.nhanhieu.ten LIKE %?3% "
			+ "OR p.danhmuc.ten LIKE %?3%)")			
	public Page<SanPham> searchInCategory(Integer categoryId, String categoryIdMatch, 
			String keyword, Pageable pageable);
	
	
	@Query("SELECT p FROM SanPham p WHERE p.ten LIKE %?1%")
	public Page<SanPham> searchSanPhamByTen(String keyword, Pageable pageable);	
}
