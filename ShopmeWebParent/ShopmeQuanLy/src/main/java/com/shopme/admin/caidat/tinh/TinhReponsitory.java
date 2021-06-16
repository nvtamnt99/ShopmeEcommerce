package com.shopme.admin.caidat.tinh;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.Tinh;

public interface TinhReponsitory extends CrudRepository<Tinh, Integer>{

	public List<Tinh> findByDatNuocOrderByTenAsc(DatNuoc datNuoc);
	
}
