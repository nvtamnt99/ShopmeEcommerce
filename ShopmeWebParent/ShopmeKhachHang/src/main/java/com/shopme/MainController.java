package com.shopme;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.danhmuc.DanhMucService;
import com.shopme.common.entity.DanhMuc;

@Controller
public class MainController {
	
	@Autowired private DanhMucService danhMucService;
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<DanhMuc> listCategories = danhMucService.listNoChildrenCategories();
		model.addAttribute("listCategories", listCategories);
		
		return "index";
	}
	
	@GetMapping("/dangnhap")
	public String showLoginForm(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//			model.addAttribute("pageTitle", "Khách hàng Đăng nhập");
			return "dangnhap";
		}
		return "redirect:/";
	}
	@GetMapping("/show_delete_modal")
	public String showDeleteModal(@Param("type") String type, @Param("id") String id,
			@Param("keyField") String keyField, Model model) {
		String deleteInfo = String.format("%s ID %s: %s?", type, id, keyField);
		String deleteURL = String.format("/%s/delete/%s", type, id);
		
		model.addAttribute("deleteTarget", deleteInfo);
		model.addAttribute("deleteURL", deleteURL);
		
		return "delete_modal";
	}	

}
