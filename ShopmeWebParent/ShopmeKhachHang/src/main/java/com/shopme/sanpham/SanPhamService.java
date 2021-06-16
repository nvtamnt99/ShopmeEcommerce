package com.shopme.sanpham;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.SanPham;
import com.shopme.common.exception.SanPhamNotFoundException;

@Service
public class SanPhamService {
	public static final int PRODUCTS_PER_PAGE = 12;
	public static final int SEARCH_RESULTS_PER_PAGE = 12;
	
	@Autowired private SanPhamRepository repo;

	public Page<SanPham> listByCategory(int pageNum, Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

		return repo.listByCategory(categoryId, categoryIdMatch, pageable);

	}
	
	public SanPham getProduct(String biDanh) throws SanPhamNotFoundException {
		SanPham product = repo.findByBiDanh(biDanh);
		if (product == null) {
			throw new SanPhamNotFoundException("Không thể tìm thấy bất kỳ sản phẩm nào có bí danh " + biDanh);
		}

		return product;
	}
	public SanPham get(Integer id) throws SanPhamNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new SanPhamNotFoundException("Không thể tìm thấy bất kỳ sản phẩm nào có ID " + id);
		}
	}
	public Page<SanPham> search(String keyword, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);

	}
}
