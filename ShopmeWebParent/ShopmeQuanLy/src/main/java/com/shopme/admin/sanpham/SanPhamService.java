package com.shopme.admin.sanpham;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.SanPham;
import com.shopme.common.exception.SanPhamNotFoundException;

@Service
@Transactional
public class SanPhamService {
	public static final int PRODUCTS_PER_PAGE = 5;
	@Autowired private SanPhamRepository repo;

	public List<SanPham> listAll() {
		return (List<SanPham>) repo.findAll();
	}
	
	public Page<SanPham> listByPage(int pageNum, String sortField, String sortDir,
					String keyword, Integer categoryId) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);

		if (keyword != null && !keyword.isEmpty()) {
			if (categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				return repo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			}
			
			return repo.findAll(keyword, pageable);
		}
		
		if (categoryId != null && categoryId > 0) {
			String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
			return repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
		}

		return repo.findAll(pageable);		
	}
	
	public SanPham save(SanPham product) {
		if (product.getMaSanPham() == null) {
			product.setThoiGianTao(new Date());
		}

		if (product.getBiDanh() == null || product.getBiDanh().isEmpty()) {
			String defaultAlias = product.getTen().replaceAll(" ", "-");
			product.setBiDanh(defaultAlias);
		} else {
			product.setBiDanh(product.getBiDanh().replaceAll(" ", "-"));
		}

		product.setThoiGianCapNhat(new Date());

		return repo.save(product);
	}
	
	public void saveProductPrice(SanPham productInForm) {
		SanPham productInDB = repo.findById(productInForm.getMaSanPham()).get();
		productInDB.setChiPhi(productInForm.getChiPhi());
		productInDB.setGiaBan(productInForm.getGiaBan());
		productInDB.setChietKhau(productInForm.getChietKhau());

		repo.save(productInDB);
	}
	
	public String checkUnique(Integer maSanPham, String ten) {
		boolean isCreatingNew = (maSanPham == null || maSanPham == 0);
		SanPham productByName = repo.findByTen(ten);

		if (isCreatingNew) {
			if (productByName != null) return "Duplicate";
		} else {
			if (productByName != null && productByName.getMaSanPham() != maSanPham) {
				return "Duplicate";
			}
		}

		return "OK";
	}
	
	public void updateProductEnabledStatus(Integer maSanPham, boolean trangThai) {
		repo.updateEnabledStatus(maSanPham, trangThai);
	}
	
	public void delete(Integer maSanPham) throws SanPhamNotFoundException {
		Long countByMaSanPham = repo.countByMaSanPham(maSanPham);

		if (countByMaSanPham == null || countByMaSanPham == 0) {
			throw new SanPhamNotFoundException("Không thể tìm thấy bất kỳ sản phẩm nào có ID " + maSanPham);			
		}

		repo.deleteById(maSanPham);
	}
	
	public SanPham get(Integer maSanPham) throws SanPhamNotFoundException {
		try {
			return repo.findById(maSanPham).get();
		} catch (NoSuchElementException ex) {
			throw new SanPhamNotFoundException("Không thể tìm thấy bất kỳ sản phẩm nào có ID " + maSanPham);
		}
	}
	public Page<SanPham> searchProducts(int pageNum, String keyword) {
		Sort sort = Sort.by("ten").ascending();
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);
		
		return repo.searchSanPhamByTen(keyword, pageable);
	}
}
