package com.shopme.admin.donhang;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.KhachHang;

@Repository
public interface DonhangReponsitory extends JpaRepository<DonHang, Integer> {

	public List<DonHang> findAll(Sort sort);
	
	@Query("SELECT o FROM DonHang o WHERE o.ho LIKE %?1% OR"
			+ " o.ten LIKE %?1% OR o.soDienThoai LIKE %?1% OR"
			+ " o.diaChi1 LIKE %?1% OR o.diaChi2 LIKE %?1% OR"
			+ " o.maBuuDien LIKE %?1% OR o.thanhPho LIKE %?1% OR"
			+ " o.tinh LIKE %?1% OR o.quocGia LIKE %?1% OR"
			+ " o.phuongThucThanhToan LIKE %?1% OR o.tinhTrangDH LIKE %?1% OR"
			+ " o.khachHang.ho LIKE %?1% OR"
			+ " o.khachHang.ten LIKE %?1%")
	public Page<DonHang> findAll(String keyword, Pageable pageable);
	
	public Long countBymaDonHang(Integer id);
	
	@Query("SELECT NEW com.shopme.common.entity.DonHang(o.maDonHang, o.thoiGianDatHang, o.chiPhi, o.tongPhu, o.tong)"
			+ " FROM DonHang o WHERE o.thoiGianDatHang BETWEEN ?1 and ?2 ORDER BY o.thoiGianDatHang ASC")
	public List<DonHang> findBythoiGianDatHangBetween(Date startTime, Date endTime);
}
