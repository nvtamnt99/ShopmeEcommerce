package com.shopme.admin.nhanhieu;

public class DanhMucDTO {
	private Integer maDanhMuc;
	private String ten;

	public DanhMucDTO() {
	}

	public DanhMucDTO(Integer maDanhMuc, String ten) {
		this.maDanhMuc = maDanhMuc;
		this.ten = ten;
	}

	public Integer getMaDanhMuc() {
		return maDanhMuc;
	}

	public void setMaDanhMuc(Integer maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}
}
