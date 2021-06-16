package com.shopme.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sanpham")
public class SanPham {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maSanPham;
	
	@Column(unique = true, length = 256, nullable = false)
	private String ten;
	
	@Column(unique = true, length = 256, nullable = false)
	private String biDanh;
	
	@Column(length = 4096, nullable = false, name = "mota_ngan")
	private String moTaNgan;
	
	@Column(length = 10000, nullable = false, name = "mota_daydu")
	private String moTaDayDu;
	
	@Column(name = "thoigian_tao")
	private Date thoiGianTao;
	
	@Column(name = "thoigian_capnhat")
	private Date thoiGianCapNhat;
	
	private boolean trangThai;
	
	@Column(name = "trongkho")
	private boolean trongKho;
	
	private float chiPhi;
	
	private float giaBan;
	
	@Column(name = "chietkhau")
	private float chietKhau;
	
	private float chieuDai;
	private float chieuRong;
	private float chieuCao;
	private float canNang;
	
	@Column(name = "hinh_anh_chinh", nullable = false)
	private String hinhAnhChinh;
	
	@ManyToOne
	@JoinColumn(name = "danhmuc_id")
	private DanhMuc danhmuc;
	
	@ManyToOne
	@JoinColumn(name = "nhanhieu_id")
	private NhanHieu nhanhieu;
	
	@OneToMany(mappedBy = "sanpham", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<HinhAnhSanPham> hinhAnh = new HashSet<>();
	
	@OneToMany(mappedBy = "sanpham", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ChiTietSanPham> chitiet = new ArrayList<>();

	public SanPham(String tenNhanhieu) {
		this.nhanhieu = new NhanHieu(tenNhanhieu);
	}

	public SanPham() {
	}

	public SanPham(Integer maSanPham) {
		this.maSanPham = maSanPham;
	}

	public Integer getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(Integer maSanPham) {
		this.maSanPham = maSanPham;
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

	public String getMoTaNgan() {
		return moTaNgan;
	}

	public void setMoTaNgan(String moTaNgan) {
		this.moTaNgan = moTaNgan;
	}

	public String getMoTaDayDu() {
		return moTaDayDu;
	}

	public void setMoTaDayDu(String moTaDayDu) {
		this.moTaDayDu = moTaDayDu;
	}

	public Date getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao(Date thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}

	public Date getThoiGianCapNhat() {
		return thoiGianCapNhat;
	}

	public void setThoiGianCapNhat(Date thoiGianCapNhat) {
		this.thoiGianCapNhat = thoiGianCapNhat;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public boolean isTrongKho() {
		return trongKho;
	}

	public void setTrongKho(boolean trongKho) {
		this.trongKho = trongKho;
	}

	public float getChiPhi() {
		return chiPhi;
	}

	public void setChiPhi(float chiPhi) {
		this.chiPhi = chiPhi;
	}

	public float getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(float giaBan) {
		this.giaBan = giaBan;
	}

	public float getChietKhau() {
		return chietKhau;
	}

	public void setChietKhau(float chietKhau) {
		this.chietKhau = chietKhau;
	}

	public float getChieuDai() {
		return chieuDai;
	}

	public void setChieuDai(float chieuDai) {
		this.chieuDai = chieuDai;
	}

	public float getChieuRong() {
		return chieuRong;
	}

	public void setChieuRong(float chieuRong) {
		this.chieuRong = chieuRong;
	}

	public float getChieuCao() {
		return chieuCao;
	}

	public void setChieuCao(float chieuCao) {
		this.chieuCao = chieuCao;
	}

	public float getCanNang() {
		return canNang;
	}

	public void setCanNang(float canNang) {
		this.canNang = canNang;
	}

	public DanhMuc getDanhmuc() {
		return danhmuc;
	}

	public void setDanhmuc(DanhMuc danhmuc) {
		this.danhmuc = danhmuc;
	}

	public NhanHieu getNhanhieu() {
		return nhanhieu;
	}

	public void setNhanhieu(NhanHieu nhanhieu) {
		this.nhanhieu = nhanhieu;
	}

	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", ten=" + ten + "]";
	}

	public String getHinhAnhChinh() {
		return hinhAnhChinh;
	}

	public void setHinhAnhChinh(String hinhAnhChinh) {
		this.hinhAnhChinh = hinhAnhChinh;
	}

	public Set<HinhAnhSanPham> getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(Set<HinhAnhSanPham> hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	
	public void themHinhAnh(String tenHinhAnh) {
		this.hinhAnh.add(new HinhAnhSanPham(tenHinhAnh, this));
	}
	
	@Transient
	public String getDuongDanHinhAnhChinh() {
		if (maSanPham == null || hinhAnhChinh == null) return "/images/image-thumbnail.png";

		return "/product-images/" + this.maSanPham + "/" + this.hinhAnhChinh;
	}

	public List<ChiTietSanPham> getChitiet() {
		return chitiet;
	}

	public void setChitiet(List<ChiTietSanPham> chitiet) {
		this.chitiet = chitiet;
	}
	
	public void themChiTietSP(String ten, String value) {
		this.chitiet.add(new ChiTietSanPham(ten, value, this));
	}
	
	public void themChiTiet(Integer maSanPham, String ten, String value) {
		this.chitiet.add(new ChiTietSanPham(maSanPham, ten, value, this));
	}

	public boolean containsImageName(String imageName) {
		Iterator<HinhAnhSanPham> iterator = hinhAnh.iterator();

		while (iterator.hasNext()) {
			HinhAnhSanPham image = iterator.next();
			if (image.getTen().equals(imageName)) {
				return true;
			}
		}

		return false;
	}
	
	@Transient
	public String getTenNgan() {
		if (ten.length() > 70) {
			return ten.substring(0, 70).concat("...");
		}
		return ten;
	}
	
	@Transient
	public float getGiamGia() {
		if (chietKhau > 0) {
			return giaBan * ((100 - chietKhau) / 100);
		}
		return this.giaBan;
	}
	
	@Transient
	public String getURI() {
		return "/p/" + this.biDanh + "/";
	}
	
}
