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
@Table(name = "chitiet_donhang")
public class ChiTietDonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maChiTietDonHang;
	
	private int soLuong;
	
	private float chiPhi;
	
	private float ship;
	
	@Column(name = "don_gia")
	private float donGia;
	
	private float tongphu;
	
	@ManyToOne
	@JoinColumn(name = "donhang_id")
	private DonHang donhang;
	
	@ManyToOne
	@JoinColumn(name = "sanpham_id")
	private SanPham sanpham;

	public ChiTietDonHang() {
	}
	
	public ChiTietDonHang(String categoryName, float subtotal, float cost, float ship, int quantity) {
		this.tongphu = subtotal;
		this.chiPhi = cost;
		this.ship = ship;
		this.sanpham = new SanPham(categoryName);
		this.soLuong = quantity;
	}
	
	public ChiTietDonHang(float subtotal, float cost, float ship, String productName, int quantity) {
		this.tongphu = subtotal;
		this.chiPhi = cost;
		this.ship = ship;
		this.sanpham = new SanPham();
		this.sanpham.setTen(productName);
		this.soLuong = quantity;
	}	
	public Integer getMaChiTietDonHang() {
		return maChiTietDonHang;
	}


	public void setMaChiTietDonHang(Integer maChiTietDonHang) {
		this.maChiTietDonHang = maChiTietDonHang;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public float getChiPhi() {
		return chiPhi;
	}

	public void setChiPhi(float chiPhi) {
		this.chiPhi = chiPhi;
	}

	public float getShip() {
		return ship;
	}

	public void setShip(float ship) {
		this.ship = ship;
	}

	public float getDonGia() {
		return donGia;
	}

	public void setDonGia(float donGia) {
		this.donGia = donGia;
	}

	public float getTongphu() {
		return tongphu;
	}

	public void setTongphu(float tongphu) {
		this.tongphu = tongphu;
	}

	public DonHang getDonhang() {
		return donhang;
	}

	public void setDonhang(DonHang donhang) {
		this.donhang = donhang;
	}

	public SanPham getSanpham() {
		return sanpham;
	}

	public void setSanpham(SanPham sanpham) {
		this.sanpham = sanpham;
	}
	
}
