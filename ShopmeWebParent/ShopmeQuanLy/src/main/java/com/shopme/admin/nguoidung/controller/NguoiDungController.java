package com.shopme.admin.nguoidung.controller;

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
import com.shopme.admin.nguoidung.NguoiDungNotFoundException;
import com.shopme.admin.nguoidung.NguoiDungService;
import com.shopme.admin.nguoidung.export.NguoiDungCsvExporter;
import com.shopme.admin.nguoidung.export.NguoiDungExcelExporter;
import com.shopme.admin.nguoidung.export.NguoiDungPDFExporter;
import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

@Controller
public class NguoiDungController {
	@Autowired
	private NguoiDungService service;
	
	@GetMapping("/nguoidung")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "nHo", "asc", null);
	}
	
	@GetMapping("/nguoidung/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword
			) {
		
		Page<TaiKhoan> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		
		List<TaiKhoan> listUsers = page.getContent();
		
		long startCount = (pageNum - 1) * NguoiDungService.USERS_PER_PAGE + 1;
		long endCount = startCount + NguoiDungService.USERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "nguoidung/nguoidung";
	}
	
	@GetMapping("/nguoidung/new")
	public String newUser(Model model) {
		List<PhanQuyen> listRoles = service.listRoles();
		
		TaiKhoan user = new TaiKhoan();
		user.setTrangThai(true);
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Tạo tài khoản mới");
		
		return "nguoidung/nguoidung_form";
	}
	
	@PostMapping("/nguoidung/save")
	public String saveUser(TaiKhoan user, RedirectAttributes redirectAttributes,
			 @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setHinhAnh(fileName);
			TaiKhoan savedUser = service.save(user);
			
			String uploadDir = "user-photos/" + savedUser.getMaTK();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (user.getHinhAnh().isEmpty()) user.setHinhAnh(null);
			service.save(user);
		}
		redirectAttributes.addFlashAttribute("message", "Thông tin người dùng đã được lưu thành công");
		
		return getRedirectURLtoAffectedTaiKhoan(user);
	}

	private String getRedirectURLtoAffectedTaiKhoan(TaiKhoan user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/nguoidung/page/1?sortField=maTK&sortDir=asc&keyword=" + firstPartOfEmail;
	}
	
	@GetMapping("/nguoidung/edit/{maTK}")
	public String editUser(@PathVariable(name = "maTK") Integer maTK,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			TaiKhoan user = service.get(maTK);
			List<PhanQuyen> listRoles = service.listRoles();
			
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Chỉnh sửa thông tin người dùng (mã tài khoản: " + maTK + ")");
			model.addAttribute("listRoles", listRoles);
			
			return "nguoidung/nguoidung_form";
		} catch (NguoiDungNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/nguoidung";
		}
	}
	
	@GetMapping("/nguoidung/delete/{maTK}")
	public String deleteUser(@PathVariable(name = "maTK") Integer maTK,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(maTK);;
			redirectAttributes.addFlashAttribute("message", "Xóa người dùng có mã tài khoản: " 
					+ maTK + " thành công");
		} catch (NguoiDungNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/nguoidung";
	}
	
	@GetMapping("nguoidung/{maTK}/trangThai/{itrangthai}") 
	public String capNhatTrangThaiKichHoatNguoiDung(@PathVariable("maTK") Integer maTK,
			@PathVariable ("itrangthai") boolean trangThai, RedirectAttributes redirectAttributes) {
		service.capNhatTrangThaiNguoiDung(maTK, trangThai);
		String itrangthai = trangThai? " kích hoạt" : " tắt kích hoạt";
		String thongbao = "Người dùng có mã tài khoản " + maTK + " đã" + itrangthai;
		redirectAttributes.addFlashAttribute("message", thongbao);
		return "redirect:/nguoidung";
		
		}
	
	@GetMapping("/nguoidung/export/csv")
	public void xuatFileCSV(HttpServletResponse response) throws IOException {
		List<TaiKhoan> listUsers = service.listAll();
		NguoiDungCsvExporter exporter = new NguoiDungCsvExporter();
		exporter.export(listUsers, response);
	}
	
	@GetMapping("/nguoidung/export/excel")
	public void xuatFileExcel(HttpServletResponse response) throws IOException {
		List<TaiKhoan> listUsers = service.listAll();
		
		NguoiDungExcelExporter exporter = new NguoiDungExcelExporter();
		exporter.export(listUsers, response);
	}
	
	@GetMapping("/nguoidung/export/pdf")
	public void xuatFilePDF(HttpServletResponse response) throws IOException {
		List<TaiKhoan> listUsers = service.listAll();
		
		NguoiDungPDFExporter exporter = new NguoiDungPDFExporter();
		exporter.export(listUsers, response);
	}
	
}