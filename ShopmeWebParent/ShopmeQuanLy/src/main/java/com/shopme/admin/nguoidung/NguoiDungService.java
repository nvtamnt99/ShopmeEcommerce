package com.shopme.admin.nguoidung;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

@Service
@Transactional
public class NguoiDungService {
	public static final int USERS_PER_PAGE = 4;
	
	@Autowired
	private NguoiDungRepository userRepo;
	
	@Autowired
	private PhanQuyenRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public TaiKhoan getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	public List<TaiKhoan> listAll(){
		return (List<TaiKhoan>) userRepo.findAll(Sort.by("nHo").ascending());
	}
	
	public List<PhanQuyen> listRoles(){
		return (List<PhanQuyen>) roleRepo.findAll();
	}
	
	public Page<TaiKhoan> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum -1, USERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return userRepo.timKiem(keyword, pageable);
		}
		
		return userRepo.findAll(pageable);
	}

	public TaiKhoan save(TaiKhoan user) {
		boolean isUpdatingUser = (user.getMaTK() != null);
		
		if(isUpdatingUser) {
			TaiKhoan existingUser = userRepo.findById(user.getMaTK()).get();
			
			if (user.getMatKhau().isEmpty()) {
				user.setMatKhau(existingUser.getMatKhau());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		
		return userRepo.save(user);	
	}
	
	public TaiKhoan updateAccount(TaiKhoan userInForm) {
		TaiKhoan userInDB = userRepo.findById(userInForm.getMaTK()).get();
		
		if (!userInForm.getMatKhau().isEmpty()) {
			userInDB.setMatKhau(userInForm.getMatKhau());
			encodePassword(userInDB);
		}
		
		if (userInForm.getHinhAnh() != null) {
			userInDB.setHinhAnh(userInForm.getHinhAnh());
		}
		
		userInDB.setHo(userInForm.getHo());
		userInDB.setTen(userInForm.getTen());
		
		return userRepo.save(userInDB);
	}
	
	private void encodePassword(TaiKhoan user) {
		String encodedPassword = passwordEncoder.encode(user.getMatKhau());
		user.setMatKhau(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer maTK, String email) {
		TaiKhoan userByEmail = userRepo.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (maTK == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getMaTK() != maTK) {
				return false;
			}
		}
		
		return true;
	}

	public TaiKhoan get(Integer maTK) throws NguoiDungNotFoundException {
		try {
			return userRepo.findById(maTK).get();
		} catch (NoSuchElementException ex) {
			throw new NguoiDungNotFoundException("Không tìm thấy người dùng có mã tài khoản: " + maTK);
		}
	}
	
	public void delete(Integer maTK) throws NguoiDungNotFoundException {
		Long countByMaTK = userRepo.countByMaTK(maTK);
		if (countByMaTK == null || countByMaTK == 0) {
			throw new NguoiDungNotFoundException("Không tìm thấy người dùng có mã tài khoản: " + maTK);
		}
		
		userRepo.deleteById(maTK);
	}
	
	public void capNhatTrangThaiNguoiDung(Integer maTK, boolean trangThai) {
		userRepo.capNhatTrangThai(maTK, trangThai);
	}
}