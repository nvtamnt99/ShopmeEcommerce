package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "danhmuc")
public class DanhMuc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDanhMuc;
	
	@Column(length = 128, nullable = false, unique = true)
	private String ten;
	
	@Column(length = 64, nullable = false, unique = true)
	private String biDanh;
	
	@Column(length = 128, nullable = false)
	private String hinhAnh;
	
	private boolean trangThai;
	
	@Column(name = "tatca_cha_id", length = 256, nullable = true)
	private String tatCaMaDanhMucCha;
	
	@OneToOne
	@JoinColumn(name = "danhMucCha_id")
	private DanhMuc danhMucCha;
	
	@OneToMany(mappedBy = "danhMucCha")
	@OrderBy("ten asc")
	private Set<DanhMuc> danhMuccon = new HashSet<>();

	public DanhMuc() {
	}
	
	public DanhMuc(Integer maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}
	
	public static DanhMuc copyMaVaTen(DanhMuc danhmuc) {
		DanhMuc copyDanhMuc = new DanhMuc();
		copyDanhMuc.setMaDanhMuc(danhmuc.getMaDanhMuc());
		copyDanhMuc.setTen(danhmuc.getTen());

		return copyDanhMuc;
	}

	public static DanhMuc copyMaVaTen(Integer maDanhMuc, String ten) {
		DanhMuc copyDanhMuc = new DanhMuc();
		copyDanhMuc.setMaDanhMuc(maDanhMuc);
		copyDanhMuc.setTen(ten);

		return copyDanhMuc;
	}
	
	public static DanhMuc copyTatCa(DanhMuc danhmuc) {
		DanhMuc copyDanhMuc = new DanhMuc();
		copyDanhMuc.setMaDanhMuc(danhmuc.getMaDanhMuc());
		copyDanhMuc.setTen(danhmuc.getTen());
		copyDanhMuc.setHinhAnh(danhmuc.getHinhAnh());
		copyDanhMuc.setBiDanh(danhmuc.getBiDanh());
		copyDanhMuc.setTrangThai(danhmuc.isTrangThai());
		copyDanhMuc.setHasDanhMucCon(danhmuc.getDanhMuccon().size() > 0);
		
		return copyDanhMuc;
	}
	
	public static DanhMuc copyTatCa(DanhMuc danhmuc, String ten) { 
		DanhMuc copyDanhMuc = DanhMuc.copyTatCa(danhmuc);
		copyDanhMuc.setTen(ten);
		
		return copyDanhMuc;
	}

	public DanhMuc(String ten) {
		this.ten = ten;
		this.biDanh = ten;
		this.hinhAnh = "default.png";
	}
	
	public DanhMuc(String ten, DanhMuc danhMucCha) {
		this(ten);
		this.danhMucCha = danhMucCha;
	}
	
	public DanhMuc(Integer maDanhMuc, String ten, String biDanh) {
		super();
		this.maDanhMuc = maDanhMuc;
		this.ten = ten;
		this.biDanh = biDanh;
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

	public String getBiDanh() {
		return biDanh;
	}

	public void setBiDanh(String biDanh) {
		this.biDanh = biDanh;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public DanhMuc getDanhMucCha() {
		return danhMucCha;
	}

	public void setDanhMucCha(DanhMuc danhMucCha) {
		this.danhMucCha = danhMucCha;
	}

	public Set<DanhMuc> getDanhMuccon() {
		return danhMuccon;
	}

	public void setDanhMuccon(Set<DanhMuc> danhMuccon) {
		this.danhMuccon = danhMuccon;
	}
	
	@Transient
	public String getDuongDanHinhAnh() {
		if (this.maDanhMuc == null) return "/images/image-thumbnail.png";
		
		return "/category-images/" + this.maDanhMuc + "/" + this.hinhAnh;
	}
	
	public boolean isHasDanhMucCon() {
		return hasDanhMucCon;
	}
	
	public void setHasDanhMucCon(boolean hasDanhMucCon) {
		this.hasDanhMucCon = hasDanhMucCon;
	}
	
	@Transient
	private boolean hasDanhMucCon;

	@Override
	public String toString() {
		return this.ten;
	}

	public String getTatCaMaDanhMucCha() {
		return tatCaMaDanhMucCha;
	}

	public void setTatCaMaDanhMucCha(String tatCaMaDanhMucCha) {
		this.tatCaMaDanhMucCha = tatCaMaDanhMucCha;
	}
	
	
}
