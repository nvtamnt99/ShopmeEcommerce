package com.shopme.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User{
	
	private String clientName;
	private String fullName;
	private OAuth2User oauth2User;
	
	public CustomOAuth2User(OAuth2User oauth2User, String clientName) {
		this.oauth2User = oauth2User;
		this.clientName = clientName;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return oauth2User.getAttribute("name");
	}
	public String getEmail() {
		return oauth2User.getAttribute("email");
	}
	public String getHoTen() {
		return oauth2User.getAttribute("name");
	}

	public String getClientName() {
		return clientName;
	}
	public String getFullName() {
		return fullName != null ? fullName : oauth2User.getAttribute("name");
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	} 
}
