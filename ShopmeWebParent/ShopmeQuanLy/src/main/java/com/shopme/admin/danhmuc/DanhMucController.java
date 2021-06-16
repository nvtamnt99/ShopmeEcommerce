package com.shopme.admin.danhmuc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.shopme.common.entity.DanhMuc;
import com.shopme.common.exception.DanhMucNotFoundException;

@Controller
public class DanhMucController {
	@Autowired
	private DanhMucService service;
	
	@GetMapping("/danhmuc")
	public String listFirstPage(@Param("sortDir") String sortDir, Model model) {
		return listByPage(1, sortDir, null, model);
	} 
	
	@GetMapping("/danhmuc/page/{pageNum}") 
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, 
			@Param("sortDir") String sortDir,
			@Param("keyword") String keyword,
			Model model) {
		if (sortDir ==  null || sortDir.isEmpty()) {
			sortDir = "asc";
		}
		
		DanhMucPageInfo pageInfo = new DanhMucPageInfo();
		List<DanhMuc> listCategories = service.listByPage(pageInfo, pageNum, sortDir, keyword);
		
		long startCount = (pageNum - 1) * DanhMucService.ROOT_CATEGORIES_PER_PAGE + 1;
		long endCount = startCount + DanhMucService.ROOT_CATEGORIES_PER_PAGE - 1;
		if (endCount > pageInfo.getTotalElements()) {
			endCount = pageInfo.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("totalPages", pageInfo.getTotalPages());
		model.addAttribute("totalItems", pageInfo.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("sortField", "name");
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("reverseSortDir", reverseSortDir);
		
		return "danhmuc/danhmuc";
		
	}
	
	@GetMapping("/danhmuc/new")
	public String newCategory(Model model) {
		List<DanhMuc> listCategories = service.listCategoriesUsedInForm();

		model.addAttribute("category", new DanhMuc());
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Tạo danh mục mới");

		return "danhmuc/danhmuc_form";
	}
	
	@PostMapping("/danhmuc/save")
	public String saveCategory(DanhMuc category, 
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setHinhAnh(fileName);

			DanhMuc savedCategory = service.save(category);
			String uploadDir = "../category-images/" + savedCategory.getMaDanhMuc();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			service.save(category);
		}

		ra.addFlashAttribute("message", "Danh mục đã được lưu thành công.");
		return "redirect:/danhmuc";
	}
	
	@GetMapping("/danhmuc/edit/{maDanhMuc}")
	public String editCategory(@PathVariable(name = "maDanhMuc") Integer maDanhMuc, Model model,
			RedirectAttributes ra) {
		try {
			DanhMuc category = service.get(maDanhMuc);
			List<DanhMuc> listCategories = service.listCategoriesUsedInForm();

			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit Category (ID: " + maDanhMuc + ")");

			return "danhmuc/danhmuc_form";			
		} catch (DanhMucNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/danhmuc";
		}
	}
	
	@GetMapping("/danhmuc/{maDanhMuc}/trangThai/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("maDanhMuc") Integer maDanhMuc,
			@PathVariable("status") boolean trangThai, RedirectAttributes redirectAttributes) {
		service.updateCategoryEnabledStatus(maDanhMuc, trangThai);
		String status = trangThai ? "bật kích hoạt" : "tắt kích hoạt";
		String message = "Danh mục có ID " + maDanhMuc + " đã được " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/danhmuc";
	}
	
	@GetMapping("/danhmuc/delete/{maDanhMuc}")
	public String deleteCategory(@PathVariable(name = "maDanhMuc") Integer maDanhMuc, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(maDanhMuc);
			String categoryDir = "../category-images/" + maDanhMuc;
			FileUploadUtil.removeDir(categoryDir);

			redirectAttributes.addFlashAttribute("message", 
					"Danh mục có ID " + maDanhMuc + " đã được xóa thành công");
		} catch (DanhMucNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/danhmuc";
	}
	
	@GetMapping("/danhmuc/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<DanhMuc> listCategories = service.listCategoriesUsedInForm();
		DanhMucCsvExporter exporter = new DanhMucCsvExporter();
		exporter.export(listCategories, response);
	}
}
