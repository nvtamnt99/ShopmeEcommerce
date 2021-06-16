package com.shopme.donhang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.ChiTietDonHang;
import com.shopme.common.entity.TinhTrangDonHang;

@Repository
public interface ChitietdonhangRepository extends JpaRepository<ChiTietDonHang, Integer>{

	@Query("SELECT COUNT(DISTINCT od) FROM ChiTietDonHang od JOIN TheoDoiDonHang ot ON "
			+ "od.donhang.maDonHang = ot.donhang.maDonHang"
			+ " WHERE od.sanpham.maSanPham = ?1 AND od.donhang.khachHang.maKhachHang = ?2"
			+ " AND ot.tinhTrangDonHang = ?3")
	public Long countBysanPhamAndkhacHangAndtinhTrangDonHang(
			Integer productId, Integer customerId, TinhTrangDonHang status);
	
}
