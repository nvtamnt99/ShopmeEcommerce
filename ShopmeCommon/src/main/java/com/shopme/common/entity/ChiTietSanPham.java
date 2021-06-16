package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sanpham_chitiet")
public class ChiTietSanPham {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maChiTietSP;
	
	@Column(nullable = false, length = 255)
	private String ten;
	
	@Column(nullable = false, length = 255)
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "sanpham_id")
	private SanPham sanpham;

	public ChiTietSanPham() {
	}
	
	

	public ChiTietSanPham(Integer maChiTietSP, String ten, String value, SanPham sanpham) {
		super();
		this.maChiTietSP = maChiTietSP;
		this.ten = ten;
		this.value = value;
		this.sanpham = sanpham;
	}



	public ChiTietSanPham(String ten, String value, SanPham sanpham) {
		this.ten = ten;
		this.value = value;
		this.sanpham = sanpham;
	}

	public Integer getMaChiTietSP() {
		return maChiTietSP;
	}

	public void setMaChiTietSP(Integer maChiTietSP) {
		this.maChiTietSP = maChiTietSP;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SanPham getSanpham() {
		return sanpham;
	}

	public void setSanpham(SanPham sanpham) {
		this.sanpham = sanpham;
	}
	
	
	
}
