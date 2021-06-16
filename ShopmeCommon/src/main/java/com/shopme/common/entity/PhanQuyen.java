package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phanquyen")
public class PhanQuyen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maPhanQuyen;
	
	@Column(length = 40, nullable = false, unique = true)
	private String ten;
	
	@Column(length = 150, nullable = false)
	private String moTa;
	
	
	
	public PhanQuyen() {
	}
	

	public PhanQuyen(Integer maPhanQuyen) {
		this.maPhanQuyen = maPhanQuyen;
	}


	public PhanQuyen(String ten) {
		this.ten = ten;
	}

	public PhanQuyen(String ten, String moTa) {
		this.ten = ten;
		this.moTa = moTa;
	}

	public Integer getMaPhanQuyen() {
		return maPhanQuyen;
	}

	public void setMaPhanQuyen(Integer maPhanQuyen) {
		this.maPhanQuyen = maPhanQuyen;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maPhanQuyen == null) ? 0 : maPhanQuyen.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhanQuyen other = (PhanQuyen) obj;
		if (maPhanQuyen == null) {
			if (other.maPhanQuyen != null)
				return false;
		} else if (!maPhanQuyen.equals(other.maPhanQuyen))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.ten;
	}
	
}
