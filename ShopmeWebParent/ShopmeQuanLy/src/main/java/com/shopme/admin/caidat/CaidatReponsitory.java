package com.shopme.admin.caidat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TheloaiCaiDat;

public interface CaidatReponsitory extends JpaRepository<CaiDat, String> {

	public List<CaiDat> findBytheLoai(TheloaiCaiDat theLoai);
}
