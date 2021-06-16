package com.shopme.caidat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.Tinh;
import com.shopme.common.entity.TinhDTO;
import com.shopme.khachhang.TinhReponsitory;

@RestController
public class TinhRestController {
@Autowired private TinhReponsitory repo;
	
	@GetMapping("/caidat/list_states_by_country/{id}")
	public List<TinhDTO> listByCountry(@PathVariable("id") Integer countryId) {
		List<Tinh> listStates = repo.findByDatNuocOrderByTenAsc(new DatNuoc(countryId));
		List<TinhDTO> result = new ArrayList<>();
		
		for (Tinh state : listStates) {
			result.add(new TinhDTO(state.getMaTinh(), state.getTen()));
		}
		
		return result;
	}
}
