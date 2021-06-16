package com.shopme.diachi;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.KhachHang;

@Service
@Transactional
public class DiachiService  {

	@Autowired private DiachiReponsitory diaChiRepo;
	
	public List<DiaChi> listAddressesOf(KhachHang customer) {
		return diaChiRepo.findByKhachHang(customer);
	}
	public DiaChi getDefaultDiachiOf(KhachHang khachHang) {
		return diaChiRepo.findDefaultByKhachhang(khachHang.getMaKhachHang());
	}
	
	public void save(DiaChi address, KhachHang customer) {
		DiaChi savedAddress = diaChiRepo.save(address);
		
		if (savedAddress.isDefaultSelection()) {
			diaChiRepo.setNonDefaultToOthers(savedAddress.getMa(), customer.getMaKhachHang());
		}		
	}
	
	public DiaChi getDefaultAddressOf(KhachHang customer) {
		return diaChiRepo.findDefaultByKhachhang(customer.getMaKhachHang());
	}
	
	public void setDefaultAddress(Integer defaultAddressId, KhachHang customer) {
		if (defaultAddressId == 0) {
			// use customer's address - set non-default to all other addresses
			diaChiRepo.setNonDefaultToOthers(0, customer.getMaKhachHang());
		} else if (defaultAddressId > 0) {
			diaChiRepo.setchonMacdinh(defaultAddressId);
			diaChiRepo.setNonDefaultToOthers(defaultAddressId, customer.getMaKhachHang());
		}
	}
	
	public DiaChi get(Integer id, KhachHang customer) {
		return diaChiRepo.findByMaAndKhachhang(id, customer.getMaKhachHang());
	}
	
	public void delete(Integer id, KhachHang customer) {
		diaChiRepo.deleteByMaAndKhachhang(id, customer.getMaKhachHang());
	}
}
