package com.shopme.admin.nguoidung.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.nguoidung.NguoiDungService;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.common.entity.TaiKhoan;

@Controller
public class TaiKhoanController {
	
	@Autowired
	private NguoiDungService service;
	
	@GetMapping("/taikhoan")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser,
			Model model) {
		String email = loggedUser.getUsername();
		TaiKhoan user = service.getByEmail(email);
		model.addAttribute("user", user);
		
		return "nguoidung/taikhoan_form";
	}
	
	@PostMapping("/taikhoan/update")
	public String saveDetails(TaiKhoan user, RedirectAttributes redirectAttributes,
			 @AuthenticationPrincipal ShopmeUserDetails loggedUser,
			 @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setHinhAnh(fileName);
			TaiKhoan savedUser = service.updateAccount(user);
			
			String uploadDir = "user-photos/" + savedUser.getMaTK();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (user.getHinhAnh().isEmpty()) user.setHinhAnh(null);
			service.updateAccount(user);
		}
		
		loggedUser.setHo(user.getHo());
		loggedUser.setTen(user.getTen());
		
		redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin người dùng thành công");
		
		return "redirect:/taikhoan";
	}
}
