package com.shopme.common.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "datnuoc")
public class DatNuoc {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDatnuoc;
	
	@Column(length = 45, nullable = false, unique = true)
	private String ten;
	
	@Column(name = "ma_Vung")
	private String maVung;
	
	@OneToMany(mappedBy = "datNuoc")
	private Set<Tinh> dsTinh;

	public DatNuoc() {
		
	}

	public DatNuoc(Integer ma, String ten) {
		this.maDatnuoc = ma;
		this.ten = ten;
	}

	public DatNuoc(String ten, String maSo) {
		this.ten = ten;
		this.maVung = maSo;
	}

	public DatNuoc(Integer ma) {
		this.maDatnuoc = ma;
	}

	public DatNuoc( String ten) {
		this.ten = ten;
	}

	public Integer getMaDatnuoc() {
		return maDatnuoc;
	}

	public void setMaDatnuoc(Integer maDatnuoc) {
		this.maDatnuoc = maDatnuoc;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMaVung() {
		return maVung;
	}

	public void setMaVung(String maVung) {
		this.maVung = maVung;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maDatnuoc == null) ? 0 : maDatnuoc.hashCode());
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
		DatNuoc other = (DatNuoc) obj;
		if (maDatnuoc == null) {
			if (other.maDatnuoc != null)
				return false;
		} else if (!maDatnuoc.equals(other.maDatnuoc))
			return false;
		return true;
	}

//	@Override
//	public String toString() {
//		return "DatNuoc [maDatnuoc=" + maDatnuoc + ", ten=" + ten + ", maVung=" + maVung + "]";
//	}

	@Override
	public String toString() {
		return ten;
	}
	

}
