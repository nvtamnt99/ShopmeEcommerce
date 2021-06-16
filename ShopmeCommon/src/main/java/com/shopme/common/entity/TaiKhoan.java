package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "taikhoan")
public class TaiKhoan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maTK;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@Column(length = 64, nullable = false)
	private String matKhau;
	
	@Column(name = "ho", length = 45, nullable = false)
	private String nHo;
	
	@Column(name = "ten", length = 45, nullable = false)
	private String nTen;
	
	@Column(length = 64)
	private String hinhAnh;
	
	private boolean trangThai;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "vaitro_taikhoan",
			joinColumns = @JoinColumn(name = "ma_tk"),
			inverseJoinColumns = @JoinColumn(name = "ma_pq")
			)
	private Set<PhanQuyen> phanquyen = new HashSet<>();

	public TaiKhoan() {
	}
	
	

	public TaiKhoan(String email, String matKhau, String ho, String ten) {
		this.email = email;
		this.matKhau = matKhau;
		nHo = ho;
		nTen = ten;
	}



	public Integer getMaTK() {
		return maTK;
	}

	public void setMaTK(Integer maTK) {
		this.maTK = maTK;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getHo() {
		return nHo;
	}

	public void setHo(String ho) {
		nHo = ho;
	}

	public String getTen() {
		return nTen;
	}

	public void setTen(String ten) {
		nTen = ten;
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

	public Set<PhanQuyen> getPhanquyen() {
		return phanquyen;
	}

	public void setPhanquyen(Set<PhanQuyen> phanquyen) {
		this.phanquyen = phanquyen;
	}
	
	public void themPhanQuyen(PhanQuyen pq) {
		this.phanquyen.add(pq);
	}



	@Override
	public String toString() {
		return "TaiKhoan [maTK=" + maTK + ", email=" + email + ", Ho=" + nHo + ", Ten=" + nTen + ", phanquyen="
				+ phanquyen + "]";
	}
	
	
	@Transient
	public String getDuongDanHinhAnh() {
		if (maTK == null || hinhAnh == null) return "/images/default-user.png";
		
		return "/user-photos/" + this.maTK + "/" + this.hinhAnh;
	}
	
	@Transient
	public String getHoTen() {
		return nHo + " " + nTen;
	}
	
	public boolean hasPhanQuyen(String roleName) {
		Iterator<PhanQuyen> iterator = phanquyen.iterator();

		while (iterator.hasNext()) {
			PhanQuyen role = iterator.next();
			if (role.getTen().equals(roleName)) {
				return true;
			}
		}

		return false;
	}
	
}
