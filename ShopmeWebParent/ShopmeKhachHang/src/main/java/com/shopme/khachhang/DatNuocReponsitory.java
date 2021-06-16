package com.shopme.khachhang;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.DatNuoc;

public interface DatNuocReponsitory extends CrudRepository<DatNuoc, Integer>{
	
	public List<DatNuoc> findAllByOrderByTenAsc();
	
	@Query("SELECT c FROM DatNuoc c WHERE c.maVung = ?1")
	public DatNuoc findByMaVung(String code);
	
}
