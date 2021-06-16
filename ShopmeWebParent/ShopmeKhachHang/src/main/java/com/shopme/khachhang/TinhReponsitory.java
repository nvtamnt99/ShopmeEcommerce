package com.shopme.khachhang;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.Tinh;

@Repository
public interface TinhReponsitory extends CrudRepository<Tinh, Integer>{
	
	public List<Tinh> findByDatNuocOrderByTenAsc(DatNuoc datNuoc);
}
