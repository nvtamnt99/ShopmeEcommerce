package com.shopme.danhmuc;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.DanhMuc;

public interface DanhMucRepository extends CrudRepository<DanhMuc, Integer> {
	
	@Query("SELECT c FROM DanhMuc c WHERE c.trangThai = true ORDER BY c.ten ASC")
	public List<DanhMuc> findAllTrangThai();
	
	@Query("SELECT c FROM DanhMuc c WHERE c.trangThai = true AND c.biDanh = ?1")
	public DanhMuc findByAliasEnabled(String alias);
}
