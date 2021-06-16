package com.shopme.admin.danhmuc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DanhMucRestController {
	@Autowired
	private DanhMucService service;

	@PostMapping("/danhmuc/check_unique")
	public String checkUnique(@Param("maDanhMuc") Integer maDanhMuc, @Param("ten") String ten,
			@Param("biDanh") String biDanh) {
		return service.checkUnique(maDanhMuc, ten, biDanh);
	}
}
