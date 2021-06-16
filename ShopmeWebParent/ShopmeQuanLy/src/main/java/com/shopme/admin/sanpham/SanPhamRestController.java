package com.shopme.admin.sanpham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.SanPham;
import com.shopme.common.exception.SanPhamNotFoundException;

@RestController
public class SanPhamRestController {
	@Autowired private SanPhamService service;

	@PostMapping("/sanpham/check_unique")
	public String checkUnique(@Param("maSanPham") Integer maSanPham, @Param("ten") String ten) {
		return service.checkUnique(maSanPham, ten);
	}
	
	@GetMapping("/sanpham/get/{id}")
	public SanPhamDTO getProductInfo(@PathVariable("id") Integer id) {
		try {
			SanPham product = service.get(id);
			return new SanPhamDTO(product);
		} catch (SanPhamNotFoundException e) {
			return new SanPhamDTO();
		}
	}
}
