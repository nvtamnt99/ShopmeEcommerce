package com.shopme.common.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "theodoi_donhang")
public class TheoDoiDonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maTheoDoiDH;
	
	@ManyToOne
	@JoinColumn(name = "donhang_id")
	private DonHang donhang;
	
	private String chuThich;
	
	@Column(name = "thoigian_capnhat")
	private Date thoigian_capnhat;
	
	@Enumerated(EnumType.STRING)
	private TinhTrangDonHang tinhTrangDonHang;

	public TheoDoiDonHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TheoDoiDonHang(Integer maTheoDoiDH, DonHang donhang, String chuThich, Date thoigian_capnhat,
			TinhTrangDonHang tinhTrangDonHang) {
		super();
		this.maTheoDoiDH = maTheoDoiDH;
		this.donhang = donhang;
		this.chuThich = chuThich;
		this.thoigian_capnhat = thoigian_capnhat;
		this.tinhTrangDonHang = tinhTrangDonHang;
	}

	public Integer getMaTheoDoiDH() {
		return maTheoDoiDH;
	}

	public void setMaTheoDoiDH(Integer maTheoDoiDH) {
		this.maTheoDoiDH = maTheoDoiDH;
	}

	public DonHang getDonhang() {
		return donhang;
	}

	public void setDonhang(DonHang donhang) {
		this.donhang = donhang;
	}

	public String getChuThich() {
		return chuThich;
	}

	public void setChuThich(String chuThich) {
		this.chuThich = chuThich;
	}

	public Date getThoigian_capnhat() {
		return thoigian_capnhat;
	}

	public void setThoigian_capnhat(Date thoigian_capnhat) {
		this.thoigian_capnhat = thoigian_capnhat;
	}

	public TinhTrangDonHang getTinhTrangDonHang() {
		return tinhTrangDonHang;
	}

	public void setTinhTrangDonHang(TinhTrangDonHang tinhTrangDonHang) {
		this.tinhTrangDonHang = tinhTrangDonHang;
	}
	@Transient
	public String getUpdatedTimeOnForm() {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		return dateFormatter.format(this.thoigian_capnhat);
	}
	
	public void setUpdatedTimeOnForm(String dateString) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		
		try {
			this.thoigian_capnhat = dateFormatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
