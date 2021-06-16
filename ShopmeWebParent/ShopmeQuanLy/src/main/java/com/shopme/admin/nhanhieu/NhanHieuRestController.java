package com.shopme.admin.nhanhieu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.DanhMuc;

@RestController
public class NhanHieuRestController {
	@Autowired
	private NhanHieuService service;

	@PostMapping("/nhanhieu/check_unique")
	public String checkUnique(@Param("maNhanHieu") Integer maNhanHieu, @Param("ten") String ten) {
		return service.checkUnique(maNhanHieu, ten);
	}
	
	@GetMapping("/nhanhieu/{maNhanHieu}/danhmuc")
	public List<DanhMucDTO> listCategoriesByBrand(@PathVariable(name = "maNhanHieu") Integer maNhanHieu) throws NhanHieuNotFoundRestException {
		List<DanhMucDTO> listCategories = new ArrayList<>(); 

		try {
			NhanHieu brand = service.get(maNhanHieu);
			Set<DanhMuc> categories = brand.getDanhmuc();

			for (DanhMuc category : categories) {
				DanhMucDTO dto = new DanhMucDTO(category.getMaDanhMuc(), category.getTen());
				listCategories.add(dto);
			}

			return listCategories;
		} catch (NhanHieuNotFoundException e) {
			throw new NhanHieuNotFoundRestException();
		}
	}
}
