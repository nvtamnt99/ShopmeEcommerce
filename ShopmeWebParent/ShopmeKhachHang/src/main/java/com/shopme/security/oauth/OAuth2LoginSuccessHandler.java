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

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired private KhachHangService khachHangService;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
		String name = oauth2User.getName();
		String email = oauth2User.getEmail();
		String countryCode = request.getLocale().getCountry();
		String clientName = oauth2User.getClientName();
		
		System.out.println("OAuth2LoginSuccessHandler: " + name + " | " + email + " | " + countryCode);
		System.out.println("Client Name: " + clientName);
		AuthenticationType authenticationType = getAuthenticationType(clientName);
		
		KhachHang khachHang = khachHangService.getCustomerByEmail(email);
		if(khachHang == null) {
				khachHangService.addNewCustomerUponOAuthLogin(name, email, countryCode, authenticationType);
		}else {
			khachHangService.updateAuthenticationType(khachHang, authenticationType);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	private AuthenticationType getAuthenticationType(String clientName) {
		if (clientName.equals("Google")) {
			return AuthenticationType.GOOGLE;
		} else if (clientName.equals("Facebook")) {
			return AuthenticationType.FACEBOOK;
		} else {
			return AuthenticationType.DATABASE;
		}
	}
}
