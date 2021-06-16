package com.shopme.admin.caidat;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.TienTe;

@Repository
public interface CurrencyReponsitory extends CrudRepository<TienTe, Integer>{

	public List<TienTe> findAllByOrderByTenAsc();
}
