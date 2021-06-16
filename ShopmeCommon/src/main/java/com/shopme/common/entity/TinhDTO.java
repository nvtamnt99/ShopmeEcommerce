package com.shopme.common.entity;

public class TinhDTO {

	private Integer ma;
	private String ten;
	public TinhDTO() {
	}
	public TinhDTO(Integer ma, String ten) {
		this.ma = ma;
		this.ten = ten;
	}
	public Integer getMa() {
		return ma;
	}
	public void setMa(Integer ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
}
