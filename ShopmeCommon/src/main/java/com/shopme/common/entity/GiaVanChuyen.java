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
@Table(name = "giavanchuyen")
public class GiaVanChuyen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ma;
	private float gia;
	private int soNgay;
	
	@Column(name = "hoTro_COD")
	private boolean hoTroCOD;

	@ManyToOne
	@JoinColumn(name = "ma_Datnuoc")
	private DatNuoc datNuoc;
	
	private String tinh;

	public GiaVanChuyen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GiaVanChuyen(Integer ma, float gia, int soNgay, boolean hoTroCOD, DatNuoc datNuoc, String tinh) {
		super();
		this.ma = ma;
		this.gia = gia;
		this.soNgay = soNgay;
		this.hoTroCOD = hoTroCOD;
		this.datNuoc = datNuoc;
		this.tinh = tinh;
	}

	public Integer getMa() {
		return ma;
	}

	public void setMa(Integer ma) {
		this.ma = ma;
	}

	public float getGia() {
		return gia;
	}

	public void setGia(float gia) {
		this.gia = gia;
	}

	public int getSoNgay() {
		return soNgay;
	}

	public void setSoNgay(int soNgay) {
		this.soNgay = soNgay;
	}

	public boolean isHoTroCOD() {
		return hoTroCOD;
	}

	public void setHoTroCOD(boolean hoTroCOD) {
		this.hoTroCOD = hoTroCOD;
	}

	public DatNuoc getDatNuoc() {
		return datNuoc;
	}

	public void setDatNuoc(DatNuoc datNuoc) {
		this.datNuoc = datNuoc;
	}

	public String getTinh() {
		return tinh;
	}

	public void setTinh(String tinh) {
		this.tinh = tinh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ma == null) ? 0 : ma.hashCode());
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
		GiaVanChuyen other = (GiaVanChuyen) obj;
		if (ma == null) {
			if (other.ma != null)
				return false;
		} else if (!ma.equals(other.ma))
			return false;
		return true;
	}
	
}
