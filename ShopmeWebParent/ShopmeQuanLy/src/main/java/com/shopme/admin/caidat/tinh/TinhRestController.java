package com.shopme.admin.caidat.tinh;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.Tinh;
import com.shopme.common.entity.TinhDTO;

@RestController
public class TinhRestController {
	
	@Autowired private TinhReponsitory repo;
	
	@GetMapping("/tinhs/list_by_country/{id}")
	public List<TinhDTO> listByCountry(@PathVariable("id") Integer countryId) {
		List<Tinh> listStates = repo.findByDatNuocOrderByTenAsc(new DatNuoc(countryId));
		List<TinhDTO> result = new ArrayList<>();
		
		for (Tinh state : listStates) {
			result.add(new TinhDTO(state.getMaTinh(), state.getTen()));
		}
		
		return result;
	}
	
	@PostMapping("/tinhs/save")
	public String save(@RequestBody Tinh tinh) {
		Tinh savedState = repo.save(tinh);
		return String.valueOf(savedState.getMaTinh());
	}
	
	@DeleteMapping("/tinhs/xoa/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

}
