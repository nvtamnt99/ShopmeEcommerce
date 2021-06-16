package com.shopme.admin.sanpham;

import com.shopme.common.entity.SanPham;

public class SanPhamDTO {
	private String duongDanHinhanh;
	private String ten;
	private float gia;
	private float chiPhi;
	public SanPhamDTO() {
		
	}
	public SanPhamDTO(SanPham sanPham) {
		this.duongDanHinhanh = sanPham.getDuongDanHinhAnhChinh();
		this.ten = sanPham.getTen();
		this.gia = sanPham.getGiaBan();
		this.chiPhi = sanPham.getChiPhi();
	}
	public String getDuongDanHinhanh() {
		return duongDanHinhanh;
	}
	public void setDuongDanHinhanh(String duongDanHinhanh) {
		this.duongDanHinhanh = duongDanHinhanh;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public float getGia() {
		return gia;
	}
	public void setGia(float gia) {
		this.gia = gia;
	}
	public float getChiPhi() {
		return chiPhi;
	}
	public void setChiPhi(float chiPhi) {
		this.chiPhi = chiPhi;
	}
	
}
