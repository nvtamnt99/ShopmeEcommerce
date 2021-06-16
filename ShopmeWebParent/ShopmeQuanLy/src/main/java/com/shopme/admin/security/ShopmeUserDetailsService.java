package com.shopme.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.admin.nguoidung.NguoiDungRepository;
import com.shopme.common.entity.TaiKhoan;

public class ShopmeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private NguoiDungRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TaiKhoan user = userRepo.getUserByEmail(email);
		if (user != null) {
			return new ShopmeUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Không tìm thấy người dùng có địa chỉ email: " + email);
 	}

}
