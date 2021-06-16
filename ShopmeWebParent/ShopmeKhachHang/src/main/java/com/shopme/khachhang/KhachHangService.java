package com.shopme.khachhang;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;
import com.shopme.exception.KhachHangNotFoundException;
import com.shopme.security.CustomerUserDetails;
import com.shopme.security.oauth.CustomOAuth2User;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class KhachHangService {
	
	@Autowired private DatNuocReponsitory datNuocRp;
	
	@Autowired private KhachHangReponsitory khachHangReponsive;

	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<DatNuoc> listAllCountries() {
		return (List<DatNuoc>) datNuocRp.findAll();
	}
	
	public KhachHang getCustomerByEmail(String email) {
		return khachHangReponsive.getKhachhangByEmail(email);
	}
	
	
	public void registerCustomer(KhachHang khachHang) {
		encodePassword(khachHang);		
		khachHang.setThoiGianTao(new Date());
		khachHang.setTrangThai(false);
		khachHang.setAuthenticationType(AuthenticationType.DATABASE);
		
		String randomCode = RandomString.make(64);
		khachHang.setVerificationCode(randomCode);
		
		khachHangReponsive.save(khachHang);
	}
	
	public void createNewCustomerAfterOAuthLoginSuccess(String email, String name) {
		KhachHang khachHang = new KhachHang();
		khachHang.setEmail(email);
		khachHang.setTrangThai(true);
		khachHang.setThoiGianTao(new Date());
		khachHang.setHo(name);
		
//		customer.setAuthProvider(provider);
		
		
		khachHangReponsive.save(khachHang);
	}
	public void save(KhachHang khachHang) {
		encodePassword(khachHang);
		khachHangReponsive.save(khachHang);
	}
	
	private void encodePassword(KhachHang khachHang) {
		String encodedPassword = passwordEncoder.encode(khachHang.getMatKhau());
		khachHang.setMatKhau(encodedPassword);		
	}
	
	public KhachHang getCurrentlyLoggedInCustomer(Authentication authentication) {
		if(authentication == null) 
			return null;
		
		
		KhachHang khachHang = null;
		Object principal = authentication.getPrincipal();
		
		if(principal instanceof CustomerUserDetails) {
			khachHang = ((CustomerUserDetails) principal).getKhachhang();
		} else if (principal instanceof CustomOAuth2User) {
			
			String email = ((CustomOAuth2User) principal).getEmail();
			khachHang = getCustomerByEmail(email);
		}
		
		System.out.println(khachHang);
		return khachHang;
	}
	public boolean verify(String verificationCode) {
		KhachHang customer = khachHangReponsive.findByVerificationCode(verificationCode);
		System.out.println(customer);
		
		if (customer == null || customer.isTrangThai()) {
			return false;
		} else {
			khachHangReponsive.trangThai(customer.getMaKhachHang());
			return true;
		}
	}
	public boolean isEmailUnique(String email) {
		KhachHang customer = khachHangReponsive.getKhachhangByEmail(email);
		System.out.println(customer);
		return customer == null;
	}
	public void updateAuthenticationType(KhachHang customer, AuthenticationType type) {
		if (!customer.getAuthenticationType().equals(type)) {
			khachHangReponsive.updateAuthenticationType(customer.getMaKhachHang(), type);
		}
	}
	public void addNewCustomerUponOAuthLogin(String name, String email, String countryCode, AuthenticationType authenticationType) {
		KhachHang customer = new KhachHang();
		customer.setEmail(email);
		setName(name, customer);
		
		customer.setTrangThai(true);
		customer.setThoiGianTao(new Date());
		customer.setAuthenticationType(authenticationType);
		customer.setMatKhau("");
		customer.setDiaChi1("");
		customer.setThanhPho("");
		customer.setTinh("");
		customer.setSoDienThoai("");
		customer.setMaBuuDien("");
		customer.setDatNuoc(datNuocRp.findByMaVung(countryCode));
		
		khachHangReponsive.save(customer);
	}	
	private void setName(String name, KhachHang customer) {
		String[] nameArray = name.split(" ");
		if (nameArray.length < 2) {
			customer.setHo(name);
			customer.setTen("");
		} else {
			String firstName = nameArray[0];
			customer.setHo(firstName);
			
			String lastName = name.replaceFirst(firstName + " ", "");
			customer.setTen(lastName);
		}
	}
	public void update(KhachHang customerInForm) {
		KhachHang customerInDB = khachHangReponsive.findById(customerInForm.getMaKhachHang()).get();
		
		if (customerInDB.getAuthenticationType().equals(AuthenticationType.DATABASE)) {
			if (!customerInForm.getMatKhau().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(customerInForm.getMatKhau());
				customerInForm.setMatKhau(encodedPassword);			
			} else {
				customerInForm.setMatKhau(customerInDB.getMatKhau());
			}		
		} else {
			customerInForm.setMatKhau(customerInDB.getMatKhau());
		}
		
		customerInForm.setTrangThai(customerInDB.isTrangThai());
		customerInForm.setThoiGianTao(customerInDB.getThoiGianTao());
		customerInForm.setVerificationCode(customerInDB.getVerificationCode());
		customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
//		customerInForm.setResetPasswordToken(customerInDB.getResetPasswordToken());
		
		khachHangReponsive.save(customerInForm);
	}
	public String updateResetPasswordToken(String email) throws KhachHangNotFoundException {
		KhachHang customer = khachHangReponsive.getKhachhangByEmail(email);
		if (customer != null) {
			String token = RandomString.make(30);
			customer.setResetPasswordToken(token);
			khachHangReponsive.save(customer);
			
			return token;
		} else {
			throw new KhachHangNotFoundException("Không thể tìm thấy bất kỳ khách hàng nào có emaill " + email);
		}
	}	
	
	public KhachHang getByResetPasswordToken(String token) {
		return khachHangReponsive.findByResetPasswordToken(token);
	}
	
	public void updatePassword(String token, String newPassword) throws KhachHangNotFoundException {
		KhachHang customer = khachHangReponsive.findByResetPasswordToken(token);
		if (customer == null) {
			throw new KhachHangNotFoundException("Không tìm thấy khách hàng: mã thông báo không hợp lệ");
		}
		
		customer.setMatKhau(newPassword);
		customer.setResetPasswordToken(null);
		encodePassword(customer);
		
		khachHangReponsive.save(customer);
	}
}
