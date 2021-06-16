package com.shopme.donhang;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.KhachHang;

@Repository
public interface DonhangReponsitory extends JpaRepository<DonHang, Integer> {
	
	@Query("SELECT o FROM DonHang o WHERE o.khachHang.maKhachHang = ?2 AND (o.ho LIKE %?1% OR"
			+ " o.ten LIKE %?1% OR o.soDienThoai LIKE %?1% OR"
			+ " o.diaChi1 LIKE %?1% OR o.diaChi2 LIKE %?1% OR"
			+ " o.maBuuDien LIKE %?1% OR o.thanhPho LIKE %?1% OR"
			+ " o.tinh LIKE %?1% OR o.quocGia LIKE %?1% OR"
			+ " o.phuongThucThanhToan LIKE %?1% OR o.tinhTrangDH LIKE %?1%)")
	public Page<DonHang> findAll(String keyword, Integer customerId, Pageable pageable);
	
	@Query("SELECT o FROM DonHang o WHERE o.khachHang.maKhachHang = ?1")
	public Page<DonHang> findAll(Integer customerId, Pageable pageable);
	
	public DonHang findBymaDonHangAndKhachHang(Integer id, KhachHang customer);
	
	public Long countBymaDonHang(Integer id);
}
