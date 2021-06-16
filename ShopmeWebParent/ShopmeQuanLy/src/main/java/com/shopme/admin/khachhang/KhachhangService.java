package com.shopme.admin.khachhang;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.caidat.quocgia.QuocGiaReponsitory;
import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;

@Service
@Transactional
public class KhachhangService {
	public static final int CUSTOMERS_PER_PAGE = 10;
	
	@Autowired 
	private QuocGiaReponsitory countryRepo;
	
	@Autowired
	private KhachhangReponsitory customerRepo;

	public List<KhachHang> listAll() {
		return customerRepo.findAll(Sort.by("ho").ascending());
	}
	
	public Page<KhachHang> listAll(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return customerRepo.findAll(keyword, pageable);
		}
		
		return customerRepo.findAll(pageable);
	}
	
	public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
		customerRepo.updateTrangThai(id, enabled);
	}
	
	public KhachHang get(Integer id) throws KhachhangNotFoundException {
		try {
			return customerRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new KhachhangNotFoundException("Không thể tìm thấy bất kỳ khách hàng nào có ID " + id);
		}
	}
	
	public void delete(Integer id) throws KhachhangNotFoundException {
		Long count = customerRepo.countBymaKhachHang(id);
		if (count == null || count == 0) {
			throw new KhachhangNotFoundException("Không thể tìm thấy bất kỳ khách hàng nào có ID " + id);
		}
		
		customerRepo.deleteById(id);
	}
	
	public List<DatNuoc> listAllCountries() {
		return countryRepo.findAllByOrderByTenAsc();
	}	
	
	public void save(KhachHang customer) {
		customerRepo.save(customer);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		KhachHang existCustomer = customerRepo.findByEmail(email);

		if (existCustomer != null && existCustomer.getMaKhachHang() != id) {
			return false;
		}
		
		return true;
	}
}
