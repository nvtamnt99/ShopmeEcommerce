package com.shopme.admin;

import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/dangnhap")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
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
