package com.shopme.tiente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopme.common.entity.TienTe;

public interface TienTeReponsitory extends JpaRepository<TienTe, Integer>{
	
	public List<TienTe> findAllByOrderByTenAsc();

}
