package com.shopme.common.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "donhang")
public class DonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDonHang;
	
	@Column(name = "ho")
	private String ho;
	
	@Column(name = "ten")
	private String ten;
	
	@Column(name = "so_dien_thoai")
	private String soDienThoai;
	
	@Column(name = "dia_chi1")
	private String diaChi1;
	
	@Column(name = "dia_chi2")
	private String diaChi2;
	
	@Column(name = "ma_buu_dien")
	private String maBuuDien;
	
	private String thanhPho;
	private String tinh;
	private String quocGia;
	
	private Date thoiGianDatHang;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "phuong_thuc_thanh_toan")
	private PhuongThucThanhToan phuongThucThanhToan;
	
	@Column(name = "gia_van_chuyen")
	private float giaVanChuyen;
	
	private float chiPhi;
	private float thue;
	private float tongPhu;
	private float tong;
	
	@Enumerated(EnumType.STRING)
	private TinhTrangDonHang tinhTrangDH;
	
	@Column(name = "giao_ngay")
	private int giaoNgay;
	
	@Column(name = "ngay_giao_hang")
	private Date ngayGiaoHang;
	
	@ManyToOne
	@JoinColumn(name = "khachhang_id")
	private KhachHang khachHang;
	
	@OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ChiTietDonHang> chiTietDH = new HashSet<>();
	
	@OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TheoDoiDonHang> theoDoiDH = new ArrayList<>();

	
	public DonHang() {
	}

	public DonHang(Integer maDonHang, Date thoiGianDatHang, float chiPhi, float tongPhu, float tong) {
		this.maDonHang = maDonHang;
		this.thoiGianDatHang = thoiGianDatHang;
		this.chiPhi = chiPhi;
		this.tongPhu = tongPhu;
		this.tong = tong;
	}

	public Integer getMaDonHang() {
		return maDonHang;
	}

	public void setMaDonHang(Integer maDonHang) {
		this.maDonHang = maDonHang;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi1() {
		return diaChi1;
	}

	public void setDiaChi1(String diaChi1) {
		this.diaChi1 = diaChi1;
	}

	public String getDiaChi2() {
		return diaChi2;
	}

	public void setDiaChi2(String diaChi2) {
		this.diaChi2 = diaChi2;
	}

	public String getMaBuuDien() {
		return maBuuDien;
	}

	public void setMaBuuDien(String maBuuDien) {
		this.maBuuDien = maBuuDien;
	}

	public String getThanhPho() {
		return thanhPho;
	}

	public void setThanhPho(String thanhPho) {
		this.thanhPho = thanhPho;
	}

	public String getTinh() {
		return tinh;
	}

	public void setTinh(String tinh) {
		this.tinh = tinh;
	}

	public String getQuocGia() {
		return quocGia;
	}

	public void setQuocGia(String quocGia) {
		this.quocGia = quocGia;
	}

	public Date getThoiGianDatHang() {
		return thoiGianDatHang;
	}

	public void setThoiGianDatHang(Date thoiGianDatHang) {
		this.thoiGianDatHang = thoiGianDatHang;
	}

	public PhuongThucThanhToan getPhuongThucThanhToan() {
		return phuongThucThanhToan;
	}

	public void setPhuongThucThanhToan(PhuongThucThanhToan phuongThucThanhToan) {
		this.phuongThucThanhToan = phuongThucThanhToan;
	}

	public float getGiaVanChuyen() {
		return giaVanChuyen;
	}

	public void setGiaVanChuyen(float giaVanChuyen) {
		this.giaVanChuyen = giaVanChuyen;
	}

	public float getChiPhi() {
		return chiPhi;
	}

	public void setChiPhi(float chiPhi) {
		this.chiPhi = chiPhi;
	}

	public float getThue() {
		return thue;
	}

	public void setThue(float thue) {
		this.thue = thue;
	}

	public float getTongPhu() {
		return tongPhu;
	}

	public void setTongPhu(float tongPhu) {
		this.tongPhu = tongPhu;
	}

	public float getTong() {
		return tong;
	}

	public void setTong(float tong) {
		this.tong = tong;
	}

	public TinhTrangDonHang getTinhTrangDH() {
		return tinhTrangDH;
	}

	public void setTinhTrangDH(TinhTrangDonHang tinhTrangDH) {
		this.tinhTrangDH = tinhTrangDH;
	}

	public int getGiaoNgay() {
		return giaoNgay;
	}

	public void setGiaoNgay(int giaoNgay) {
		this.giaoNgay = giaoNgay;
	}

	public Date getNgayGiaoHang() {
		return ngayGiaoHang;
	}

	public void setNgayGiaoHang(Date ngayGiaoHang) {
		this.ngayGiaoHang = ngayGiaoHang;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public Set<ChiTietDonHang> getChiTietDH() {
		return chiTietDH;
	}

	public void setChiTietDH(Set<ChiTietDonHang> chiTietDH) {
		this.chiTietDH = chiTietDH;
	}

	public List<TheoDoiDonHang> getTheoDoiDH() {
		return theoDoiDH;
	}

	public void setTheoDoiDH(List<TheoDoiDonHang> theoDoiDH) {
		this.theoDoiDH = theoDoiDH;
	}
	
	public void copyShippingAddressFromCustomer(KhachHang khachHang) {
		this.ho=khachHang.getHo();
		this.ten = khachHang.getTen();
		this.diaChi1 = khachHang.getDiaChi1();
		this.diaChi2 = khachHang.getDiaChi2();
		this.thanhPho = khachHang.getThanhPho();
		this.quocGia = khachHang.getTendatNuoc();
		this.tinh = khachHang.getTinh();
		this.soDienThoai = khachHang.getSoDienThoai();
		this.maBuuDien = khachHang.getMaBuuDien();
		
	}
	
	public void copyShippingAddress(DiaChi diaChi) {
		this.ho = diaChi.getHo();
		this.ten = diaChi.getTen();
		this.diaChi1 = diaChi.getDongDiachi1();
		this.diaChi2 = diaChi.getDongDiachi2();
		this.thanhPho = diaChi.getThanhPho();
		this.quocGia = diaChi.getDatNuoc().getTen();
		this.tinh = diaChi.getTinh();
		this.soDienThoai = diaChi.getSdt();
		this.maBuuDien = diaChi.getMaBuudien();
	}
		
	@Transient
	public String getNgaytao() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(thoiGianDatHang);
		return date;
	}
	@Transient
	public String getNgayGiao() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(ngayGiaoHang);
		return date;
	}
	@Transient
	public String getDestination() {
		String diemDen = thanhPho;
		
		if(tinh != null && !tinh.isEmpty()) {
			diemDen +="." + tinh;
		}
		
		diemDen +=", " + quocGia;
		
		return diemDen;
	}
	
	@Transient
	public String getShippingAddress() {
		String diaChi = ho;
		if(ten != null && !ten.isEmpty()) diaChi += " " + ten;
		diaChi += ", " + diaChi1;
		if(diaChi2 != null && !diaChi2.isEmpty()) diaChi += " " + diaChi2;
		diaChi += ", " + thanhPho;
		if(tinh != null && !tinh.isEmpty()) diaChi += " " + tinh;
		diaChi += ", " + quocGia;
		
		diaChi += ". Mã bưu điện: " + maBuuDien;
		diaChi += ". Số điện thoại: " + soDienThoai;
		return diaChi;
	}
	@Transient
	public String getDeliverDateOnForm() {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormatter.format(this.ngayGiaoHang);
	}
	public void setDeliverDateOnForm(String dateString) {
		System.out.println("setDeliverDateOnForm: " + dateString);
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			this.ngayGiaoHang = dateFormatter.parse(dateString);
		} catch (ParseException	 e) {
			e.printStackTrace();
		}
	}

}
