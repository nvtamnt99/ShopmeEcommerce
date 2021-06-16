package com.shopme.admin.caidat.quocgia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.DatNuoc;

@RestController
public class QuocGiaRestController {
	
	@Autowired private QuocGiaReponsitory quocGiaReponsitory;
	
	@GetMapping("/datnuocs/list")
	public List<DatNuoc> listAll() {
		return quocGiaReponsitory.findAllByOrderByTenAsc();
	}
	
	@PostMapping("/datnuocs/save")
	public String save(@RequestBody DatNuoc datNuoc) {
		DatNuoc savedCountry = quocGiaReponsitory.save(datNuoc);
		return String.valueOf(savedCountry.getMaDatnuoc());
	}
	
	@DeleteMapping("/datnuocs/xoa/{id}")
	public void delete(@PathVariable("id") Integer id) {
		quocGiaReponsitory.deleteById(id);
	}

}
