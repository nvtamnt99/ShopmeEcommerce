package com.shopme.admin.vanchuyen;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.GiaVanChuyen;

@Repository
public interface GiaVanChuyenReponsitory extends JpaRepository<GiaVanChuyen, Integer>{

	@Query("SELECT sr FROM GiaVanChuyen sr WHERE sr.datNuoc.maDatnuoc = ?1 AND sr.tinh = ?2")
	public GiaVanChuyen findByDatNuocAndTinh(Integer countryId, String state);
	
	@Query("SELECT sr FROM GiaVanChuyen sr WHERE sr.datNuoc.ten LIKE %?1% OR sr.tinh LIKE %?1%")
	public Page<GiaVanChuyen> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE GiaVanChuyen sr SET sr.hoTroCOD = ?2 WHERE sr.ma = ?1")
	@Modifying
	public void updatehoTroCOD(Integer id, boolean enabled);
	
	Long countByMa(Integer id);
}
