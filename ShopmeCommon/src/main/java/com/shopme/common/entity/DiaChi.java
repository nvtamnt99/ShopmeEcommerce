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
@Table(name = "diachi")
public class DiaChi {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ma;

	@Column(name = "ho")
	private String ho;
	
	@Column(name = "ten")
	private String ten;	
	
	@Column(name = "sdt")
	private String sdt;
	
	@Column(name = "dong_Diachi1")
	private String dongDiachi1;
	
	@Column(name = "dong_Diachi2")
	private String dongDiachi2;
	
	private String thanhPho;
	private String tinh;
	
	@Column(name = "ma_Buudien")
	private String maBuudien;
	
	@Column(name = "diachi_macdinh")
	private boolean chonMacdinh;
	
	@ManyToOne
	@JoinColumn(name = "ma_Datnuoc")
	private DatNuoc datNuoc;
	
	@ManyToOne
	@JoinColumn(name = "ma_Khachhang")
	private KhachHang khachHang;

	public Integer getMa() {
		return ma;
	}

	public void setMa(Integer ma) {
		this.ma = ma;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getDongDiachi1() {
		return dongDiachi1;
	}

	public void setDongDiachi1(String dongDiachi1) {
		this.dongDiachi1 = dongDiachi1;
	}

	public String getDongDiachi2() {
		return dongDiachi2;
	}

	public void setDongDiachi2(String dongDiachi2) {
		this.dongDiachi2 = dongDiachi2;
	}

	public String getThanhPho() {
		return thanhPho;
	}

	public void setThanhPho(String thanhPho) {
		this.thanhPho = thanhPho;
	}

	public String getTinh() {
		return tinh;
	}

	public void setTinh(String tinh) {
		this.tinh = tinh;
	}

	public String getMaBuudien() {
		return maBuudien;
	}

	public void setMaBuudien(String maBuudien) {
		this.maBuudien = maBuudien;
	}

	public boolean isChonMacdinh() {
		return chonMacdinh;
	}

	public void setChonMacdinh(boolean chonMacdinh) {
		this.chonMacdinh = chonMacdinh;
	}

	public DatNuoc getDatNuoc() {
		return datNuoc;
	}

	public void setDatNuoc(DatNuoc datNuoc) {
		this.datNuoc = datNuoc;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	
	public String toString() {
		String diachi = ho;
		
		if (ten != null && !ten.isEmpty()) diachi += " " + ten;
		
		diachi += ", " + dongDiachi1;
		
		if (dongDiachi2 != null && !dongDiachi2.isEmpty()) diachi += ", " + dongDiachi2;
		
		diachi += ", " + thanhPho;
		
		if (tinh != null && !tinh.isEmpty()) diachi += ", " + tinh;
		
		diachi += ", " + datNuoc.getTen();
		
		diachi += ". Mã bưu điện: " + maBuudien;
		diachi += ". Số điện thoại: " + sdt;
		
		return diachi;
	}
	
	public boolean isDefaultSelection() {
		return chonMacdinh;
	}
	public void setDefaultSelection(boolean chonMacdinh) {
		this.chonMacdinh = chonMacdinh;
	}
}
