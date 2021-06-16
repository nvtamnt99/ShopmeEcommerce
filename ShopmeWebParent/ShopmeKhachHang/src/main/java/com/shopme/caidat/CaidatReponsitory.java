package com.shopme.caidat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TheloaiCaiDat;

public interface CaidatReponsitory extends JpaRepository<CaiDat, String>{

//	@Modifying
//	@Query("UPDATE CaiDat s "
//			+ "SET s.giatri = ?1 WHERE s.tukhoa='currency_id'")
//	public void update(String currencyId);
	
	@Query("SELECT s FROM CaiDat s WHERE s.tuKhoa = ?1")
	public CaiDat findByTuKhoa(String tuKhoa);	
	
	
	public List<CaiDat> findBytheLoai(TheloaiCaiDat theLoai);	
	
	@Query("SELECT s FROM CaiDat s WHERE s.theLoai = ?1 OR s.theLoai = ?2")
	public List<CaiDat> findByTwotheLoai(TheloaiCaiDat catOne, TheloaiCaiDat catTwo);
}
