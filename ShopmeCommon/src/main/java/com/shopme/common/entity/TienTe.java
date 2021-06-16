package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tiente")
public class TienTe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 64)
	private String ten;
	
	@Column(nullable = false, length = 3)
	private String bieuTuong;
	
	@Column(nullable = false, length = 4)
	private String ma;
	
	public TienTe() {
		super();
	}
	public TienTe(String ten, String bieuTuong, String ma) {
		this.ten = ten;
		this.bieuTuong = bieuTuong;
		this.ma = ma;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getBieuTuong() {
		return bieuTuong;
	}
	public void setBieuTuong(String bieuTuong) {
		this.bieuTuong = bieuTuong;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	@Override
	public String toString() {
		return this.ten + " - " + this.ma + " - " + this.bieuTuong;
	}
//	@Override
//	public String toString() {
//		return "TienTe [id=" + id + ", ten=" + ten + ", bieuTuong=" + bieuTuong + ", ma=" + ma + "]";
//	}
	
}
