package com.shopme.vanchuyen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.GiaVanChuyen;

@Repository
public interface VanchuyenReponsitory extends JpaRepository<GiaVanChuyen, Integer> {

	public GiaVanChuyen findByDatNuocAndTinh(DatNuoc datNuoc, String tinh);
}
