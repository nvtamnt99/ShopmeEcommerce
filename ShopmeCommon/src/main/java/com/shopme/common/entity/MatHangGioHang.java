package com.shopme.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mathang_giohang")
public class MatHangGioHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maMHGH;
	
	@ManyToOne
	@JoinColumn(name = "khachhang_id")
	private KhachHang khachhang;
	
	@ManyToOne
	@JoinColumn(name = "sanpham_id")
	private SanPham sanpham;
	
	private int soLuong;
	
	@Transient
	private float ship;

	public MatHangGioHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MatHangGioHang(Integer maMHGH, KhachHang khachhang, SanPham sanpham, int soLuong, float ship) {
		super();
		this.maMHGH = maMHGH;
		this.khachhang = khachhang;
		this.sanpham = sanpham;
		this.soLuong = soLuong;
		this.ship = ship;
	}

	public Integer getMaMHGH() {
		return maMHGH;
	}

	public void setMaMHGH(Integer maMHGH) {
		this.maMHGH = maMHGH;
	}

	public KhachHang getKhachhang() {
		return khachhang;
	}

	public void setKhachhang(KhachHang khachhang) {
		this.khachhang = khachhang;
	}

	public SanPham getSanpham() {
		return sanpham;
	}

	public void setSanpham(SanPham sanpham) {
		this.sanpham = sanpham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public float getShip() {
		return ship;
	}

	public void setShip(float ship) {
		this.ship = ship;
	}

	@Override
	public String toString() {
		return "MatHangGioHang [maMHGH=" + maMHGH + ", khachhang=" + khachhang + ", sanpham=" + sanpham + ", soLuong="
				+ soLuong + ", ship=" + ship + "]";
	}
	
	
	@Transient
	public float getTongTien() {
		return sanpham.getGiamGia()*soLuong;
	}
	
}
