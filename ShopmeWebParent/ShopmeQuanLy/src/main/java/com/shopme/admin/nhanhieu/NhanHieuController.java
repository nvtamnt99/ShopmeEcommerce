package com.shopme.admin.nhanhieu;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.danhmuc.DanhMucService;
import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.DanhMuc;

@Controller
public class NhanHieuController {
	@Autowired
	private NhanHieuService nhanHieuService;
	
	@Autowired
	private DanhMucService danhMucService;

	@GetMapping("/nhanhieu")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "ten", "asc", null);
	}
	
	@GetMapping("/nhanhieu/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword
			) {
		Page<NhanHieu> page = nhanHieuService.listByPage(pageNum, sortField, sortDir, keyword);
		List<NhanHieu> listBrands = page.getContent();

		long startCount = (pageNum - 1) * NhanHieuService.BRANDS_PER_PAGE + 1;
		long endCount = startCount + NhanHieuService.BRANDS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);		
		model.addAttribute("listBrands", listBrands);
		
		return "nhanhieu/nhanhieu";		
	}
	
	@GetMapping("/nhanhieu/new")
	public String newBrand(Model model) {
		List<DanhMuc> listCategories = danhMucService.listCategoriesUsedInForm();

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("brand", new NhanHieu());
		model.addAttribute("pageTitle", "Tạo nhãn hiệu mới");

		return "nhanhieu/nhanhieu_form";		
	}
	
	@PostMapping("/nhanhieu/save")
	public String saveBrand(NhanHieu brand, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			brand.setLogo(fileName);

			NhanHieu savedBrand = nhanHieuService.save(brand);
			String uploadDir = "../brand-logos/" + savedBrand.getMaNhanHieu();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			nhanHieuService.save(brand);
		}

		ra.addFlashAttribute("message", "Nhãn hiệu đã được lưu thành công.");
		return "redirect:/nhanhieu";		
	}

	@GetMapping("/nhanhieu/edit/{maNhanHieu}")
	public String editBrand(@PathVariable(name = "maNhanHieu") Integer maNhanHieu, Model model,
			RedirectAttributes ra) {
		try {
			NhanHieu brand = nhanHieuService.get(maNhanHieu);
			List<DanhMuc> listCategories = danhMucService.listCategoriesUsedInForm();

			model.addAttribute("brand", brand);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Chỉnh sửa nhãn hiệu có (ID: " + maNhanHieu + ")");

			return "nhanhieu/nhanhieu_form";			
		} catch (NhanHieuNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/nhanhieu";
		}
	}

	@GetMapping("/nhanhieu/delete/{maNhanHieu}")
	public String deleteBrand(@PathVariable(name = "maNhanHieu") Integer maNhanHieu, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			nhanHieuService.delete(maNhanHieu);
			String brandDir = "../brand-logos/" + maNhanHieu;
			FileUploadUtil.removeDir(brandDir);

			redirectAttributes.addFlashAttribute("message", 
					"Nhãn hiệu có ID " + maNhanHieu + " được xóa thành công");
		} catch (NhanHieuNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/nhanhieu";
	}
	
	@GetMapping("/nhanhieu/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<NhanHieu> listBrand = nhanHieuService.listAll();

		BrandExcelExporter exporter = new BrandExcelExporter();
		exporter.export(listBrand, response);
	}
}
