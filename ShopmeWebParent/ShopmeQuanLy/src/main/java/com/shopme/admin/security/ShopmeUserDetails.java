package com.shopme.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

public class ShopmeUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private TaiKhoan user;

	public ShopmeUserDetails(TaiKhoan user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<PhanQuyen> roles = user.getPhanquyen();
		
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		
		for (PhanQuyen role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getTen()));
		}
		
		return authories;
	}

	@Override
	public String getPassword() {
		return user.getMatKhau();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isTrangThai();
	}
	
	public String getFullname() {
		return this.user.getHo() + " " + this.user.getTen();
	}
	
	public void setHo(String ho) {
		this.user.setHo(ho);
	}
	
	public void setTen(String ten) {
		this.user.setTen(ten);
	}
	
	public boolean hasPhanQuyen(String roleName) {
		return user.hasPhanQuyen(roleName);
	}
	public TaiKhoan getUser() {
		return this.user;
	}
	
}
