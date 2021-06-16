package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "nhanhieu")
public class NhanHieu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maNhanHieu;
	
	@Column(nullable = false, length = 45, unique = true)
	private String ten;
	
	@Column(nullable = false, length = 128)
	private String logo;
	
	@ManyToMany
	@JoinTable(
			name = "nhanhieu_danhmuc",
			joinColumns = @JoinColumn(name = "nhanhieu_id"),
			inverseJoinColumns = @JoinColumn(name = "danhmuc_id")
			)
	private Set<DanhMuc> danhmuc = new HashSet<>();

	public NhanHieu() {
	}
	
	public NhanHieu(String ten) {
		this.ten = ten;
		this.logo = "brand-logo.png";
	}
	
	public NhanHieu(Integer maNhanHieu, String ten) {
		this.maNhanHieu = maNhanHieu;
		this.ten = ten;
	}

	public Integer getMaNhanHieu() {
		return maNhanHieu;
	}

	public void setMaNhanHieu(Integer maNhanHieu) {
		this.maNhanHieu = maNhanHieu;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Set<DanhMuc> getDanhmuc() {
		return danhmuc;
	}

	public void setDanhmuc(Set<DanhMuc> danhmuc) {
		this.danhmuc = danhmuc;
	}

	@Override
	public String toString() {
		return "NhanHieu [maNhanHieu=" + maNhanHieu + ", ten=" + ten + ", danhmuc=" + danhmuc + "]";
	}
	
	@Transient
	public String getLogoPath() {
		if (this.maNhanHieu == null) return "/images/image-thumbnail.png";

		return "/brand-logos/" + this.maNhanHieu + "/" + this.logo;		
	}
}
