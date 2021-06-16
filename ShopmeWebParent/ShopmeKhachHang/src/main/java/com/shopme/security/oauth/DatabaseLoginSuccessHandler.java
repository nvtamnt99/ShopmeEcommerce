package com.shopme.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.KhachHang;
import com.shopme.khachhang.KhachHangService;
import com.shopme.security.CustomerUserDetails;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired private KhachHangService customerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
		KhachHang khachHang = userDetails.getKhachhang();
		customerService.updateAuthenticationType(khachHang, AuthenticationType.DATABASE);
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
