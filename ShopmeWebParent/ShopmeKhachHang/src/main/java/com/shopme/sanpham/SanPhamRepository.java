package com.shopme.sanpham;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.SanPham;

public interface SanPhamRepository extends PagingAndSortingRepository<SanPham, Integer> {
	
	@Query("SELECT p FROM SanPham p WHERE p.trangThai = true "
			+ "AND (p.danhmuc.maDanhMuc = ?1 OR p.danhmuc.tatCaMaDanhMucCha LIKE %?2%)"
			+ " ORDER BY p.ten ASC")
	public Page<SanPham> listByCategory(Integer categoryId, String categoryIDMatch, Pageable pageable);

	public SanPham findByBiDanh(String biDanh);
	
	@Query(value = "SELECT * FROM sanpham WHERE trang_thai = true AND "
			+ "MATCH(ten, mota_ngan, mota_daydu) AGAINST (?1)", 
			nativeQuery = true)
	public Page<SanPham> search(String keyword, Pageable pageable);
	
//	@Query(value = "SELECT * FROM SanPham p WHERE p.trangThai=true AND MATCH(ten, biDanh, moTaNgan, moTaDayDu) "
//			+ "AGAINST (?1)", nativeQuery = true)
//	public List<SanPham> searchForRecommendation(String keyword);
//	
//	@Query("UPDATE SanPham p SET p.tongDanhgia = p.tongDanhgia + 1 WHERE p.maSanPham = ?1")
//	@Modifying
//	public void increaseTongDanhgia(Integer productId);
//	
//	@Query("UPDATE SanPham p SET p.xepHangTrungBinh = (SELECT AVG(r.xepHang) FROM Review r WHERE r.sanPham.maSanPham = ?1) WHERE p.maSanPham = ?1")
//	@Modifying
//	public void updateXepHangTrungBinh(Integer productId);
}
