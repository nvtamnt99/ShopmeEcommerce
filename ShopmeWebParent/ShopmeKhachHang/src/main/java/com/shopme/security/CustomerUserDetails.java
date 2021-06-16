package com.shopme.security;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopme.common.entity.KhachHang;
public class CustomerUserDetails implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private KhachHang khachHang;

	
	public CustomerUserDetails(KhachHang khachHang) {
		super();
		this.khachHang = khachHang;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return khachHang.getMatKhau();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return khachHang.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return khachHang.isTrangThai();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return khachHang.isTrangThai();
	}

	public String getHoTen() {
		return khachHang.getHo() + " " + khachHang.getTen(); 
	}

	public void setHo(String ho) {
		this.khachHang.setHo(ho);
	}
	
	public void setTen(String ten) {
		this.khachHang.setTen(ten);;
	}	
	
	public KhachHang getKhachhang() {
		return this.khachHang;		
	}
}
