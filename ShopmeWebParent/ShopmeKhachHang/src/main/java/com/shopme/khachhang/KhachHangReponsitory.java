package com.shopme.khachhang;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.KhachHang;


public interface KhachHangReponsitory extends CrudRepository<KhachHang, Integer>{

	@Query(value = "SELECT * FROM khachhang c WHERE c.email = :email", nativeQuery = true)
	public KhachHang getKhachhangByEmail(@Param("email") String email);
	
	@Query("SELECT c FROM KhachHang c WHERE c.verificationCode = ?1")
	public KhachHang findByVerificationCode(String code);
	
	
	@Query("UPDATE KhachHang c SET c.trangThai = true, c.verificationCode = null WHERE c.maKhachHang = ?1")
	@Modifying
	public void trangThai(Integer id);	
	
	@Query("UPDATE KhachHang c SET c.authenticationType = ?2 WHERE c.maKhachHang = ?1")
	@Modifying
	public void updateAuthenticationType(Integer customerId, AuthenticationType type);
	
	public KhachHang findByResetPasswordToken(String token);
}
