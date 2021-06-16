package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caidat")
public class CaiDat {

	@Id
	@Column(name = "`tuKhoa`")
	private String tuKhoa;
	
	private String giaTri;
	
	@Enumerated(EnumType.STRING)
	private TheloaiCaiDat theLoai;

	public CaiDat() {
	}

	public CaiDat(String tuKhoa, String giaTri, TheloaiCaiDat theLoai) {
		this.tuKhoa = tuKhoa;
		this.giaTri = giaTri;
		this.theLoai = theLoai;
	}

	public CaiDat(String tuKhoa) {
		this.tuKhoa = tuKhoa;
	}

	public String getTuKhoa() {
		return tuKhoa;
	}

	public void setTuKhoa(String tuKhoa) {
		this.tuKhoa = tuKhoa;
	}

	public String getGiaTri() {
		return giaTri;
	}

	public void setGiaTri(String giaTri) {
		this.giaTri = giaTri;
	}

	public TheloaiCaiDat getTheLoai() {
		return theLoai;
	}

	public void setTheLoai(TheloaiCaiDat theLoai) {
		this.theLoai = theLoai;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tuKhoa == null) ? 0 : tuKhoa.hashCode());
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
		CaiDat other = (CaiDat) obj;
		if (tuKhoa == null) {
			if (other.tuKhoa != null)
				return false;
		} else if (!tuKhoa.equals(other.tuKhoa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CaiDat [tuKhoa=" + tuKhoa + ", giaTri=" + giaTri + ", theLoai=" + theLoai + "]";
	}
	
}
