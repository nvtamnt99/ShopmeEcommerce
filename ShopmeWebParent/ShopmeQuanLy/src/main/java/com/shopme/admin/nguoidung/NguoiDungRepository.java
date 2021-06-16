package com.shopme.admin.nguoidung;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.TaiKhoan;

public interface NguoiDungRepository extends PagingAndSortingRepository<TaiKhoan, Integer>{
	@Query("SELECT u FROM TaiKhoan u WHERE u.email = :email")
	public TaiKhoan getUserByEmail(@Param("email") String email);
	
	//public Long countById(Integer maTK);
	public Long countByMaTK(Integer maTK);
	
	@Query("SELECT u FROM TaiKhoan u WHERE CONCAT(u.maTK, ' ', u.email, ' ', u.nHo, ' ',"
			+ " u.nTen) LIKE %?1%")
	public Page<TaiKhoan> timKiem(String keyword, Pageable pageable);
	
	
	@Query("UPDATE TaiKhoan u SET u.trangThai = ?2 WHERE u.maTK = ?1")
	@Modifying
	public void capNhatTrangThai(Integer maTK, boolean trangThai);
//	public void updateEnabledStatus(Integer maTK, boolean trangThai);
	
	
}
