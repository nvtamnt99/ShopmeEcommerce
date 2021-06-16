package com.shopme.diachi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.KhachHang;

@Repository
public interface DiachiReponsitory extends JpaRepository<DiaChi, Integer>{

	@Query("SELECT a FROM DiaChi a WHERE a.khachHang.maKhachHang = ?1 AND a.chonMacdinh = true")
	public DiaChi findDefaultByKhachhang(Integer maKhachhang);
	
	public List<DiaChi> findByKhachHang(KhachHang customer);
	
	
	@Query("UPDATE DiaChi a SET a.chonMacdinh = false WHERE a.ma != ?1 AND a.khachHang.maKhachHang = ?2")
	@Modifying
	public void setNonDefaultToOthers(Integer defaultAddressId, Integer customerId);
	
	@Modifying
	@Query("UPDATE DiaChi a SET a.chonMacdinh = true WHERE a.ma = ?1")
	public void setchonMacdinh(Integer defaultAddressId);
	
	@Query("SELECT a FROM DiaChi a WHERE a.ma = ?1 AND a.khachHang.maKhachHang = ?2")
	public DiaChi findByMaAndKhachhang(Integer addressId, Integer customerId);
	
	@Query("DELETE FROM DiaChi a WHERE a.ma = ?1 AND a.khachHang.maKhachHang = ?2")
	@Modifying
	public void deleteByMaAndKhachhang(Integer addressId, Integer customerId);
}
