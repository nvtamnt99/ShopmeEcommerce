package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.common.entity.KhachHang;
import com.shopme.khachhang.KhachHangReponsitory;

public class CustomerUserDetailsService implements UserDetailsService{
	
	
	@Autowired private KhachHangReponsitory khachhangReponsitory;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		KhachHang khachHang = khachhangReponsitory.getKhachhangByEmail(email);
		if(khachHang != null) {
			return new CustomerUserDetails(khachHang);
		}
		throw new UsernameNotFoundException("Không tìm thấy khách hàng");
	}





}
