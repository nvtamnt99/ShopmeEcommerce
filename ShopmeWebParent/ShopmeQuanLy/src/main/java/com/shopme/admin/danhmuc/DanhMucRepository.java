package com.shopme.admin.danhmuc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.DanhMuc;

public interface DanhMucRepository extends PagingAndSortingRepository<DanhMuc, Integer> {
	
	@Query("SELECT c FROM DanhMuc c WHERE c.danhMucCha.maDanhMuc is NULL")
	public List<DanhMuc> findRootDanhMuc(Sort sort);
	
	@Query("SELECT c FROM DanhMuc c WHERE c.danhMucCha.maDanhMuc is NULL")
	public Page<DanhMuc> findRootDanhMuc(Pageable pageable);
	
	@Query("SELECT c FROM DanhMuc c WHERE c.ten LIKE %?1%")
	public Page<DanhMuc> search(String keyword, Pageable pageable);
	
	public Long countBymaDanhMuc(Integer maDanhMuc);
	
	public DanhMuc findByTen(String ten);
	
	public DanhMuc findByBiDanh(String biDanh);
	
	@Query("UPDATE DanhMuc c SET c.trangThai = ?2 WHERE c.maDanhMuc = ?1")
	@Modifying
	public void updateEnabledStatus(Integer maDanhMuc, boolean trangThai);	
}
