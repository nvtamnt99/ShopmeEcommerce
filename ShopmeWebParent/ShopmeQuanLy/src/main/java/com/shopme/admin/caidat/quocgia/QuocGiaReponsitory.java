package com.shopme.admin.caidat.quocgia;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.DatNuoc;

public interface QuocGiaReponsitory extends CrudRepository<DatNuoc, Integer>{

	public List<DatNuoc> findAllByOrderByTenAsc();
}
