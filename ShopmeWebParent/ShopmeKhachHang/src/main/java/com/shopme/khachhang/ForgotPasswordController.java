package com.shopme.khachhang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.caidat.CaidatService;

@Controller
public class ForgotPasswordController {

	@Autowired private KhachHangService customerService;
	@Autowired private CaidatService settingService;
	
	@GetMapping("/forgot_password")
	public String showRequestForm() {
		return "khachhang/forgot_password_form";
	}
}
