package com.shopme.admin.baocao;

public class BaocaoDonhangItem {
	private String dinhDanh;
	private float tongDoanhThu;
	private float mangLuoiBanhang;
	private int soLuongDonhang;
	private int soLuongSanpham;
	public BaocaoDonhangItem() {
	}
	public BaocaoDonhangItem(String dinhDanh, float tongDoanhThu, float mangLuoiBanhang) {
		this.dinhDanh = dinhDanh;
		this.tongDoanhThu = tongDoanhThu;
		this.mangLuoiBanhang = mangLuoiBanhang;
	}
	public BaocaoDonhangItem(String dinhDanh, float tongDoanhThu, float mangLuoiBanhang, int soLuongSanpham) {
		this.dinhDanh = dinhDanh;
		this.tongDoanhThu = tongDoanhThu;
		this.mangLuoiBanhang = mangLuoiBanhang;
		this.soLuongSanpham = soLuongSanpham;
	}
	
	public BaocaoDonhangItem(String dinhDanh) {
		this.dinhDanh = dinhDanh;
	}
	public String getDinhDanh() {
		return dinhDanh;
	}
	public void setDinhDanh(String dinhDanh) {
		this.dinhDanh = dinhDanh;
	}
	public float getTongDoanhThu() {
		return tongDoanhThu;
	}
	public void setTongDoanhThu(float tongDoanhThu) {
		this.tongDoanhThu = tongDoanhThu;
	}
	public float getMangLuoiBanhang() {
		return mangLuoiBanhang;
	}
	public void setMangLuoiBanhang(float mangLuoiBanhang) {
		this.mangLuoiBanhang = mangLuoiBanhang;
	}
	public int getSoLuongDonhang() {
		return soLuongDonhang;
	}
	public void setSoLuongDonhang(int soLuongDonhang) {
		this.soLuongDonhang = soLuongDonhang;
	}
	public int getSoLuongSanpham() {
		return soLuongSanpham;
	}
	public void setSoLuongSanpham(int soLuongSanpham) {
		this.soLuongSanpham = soLuongSanpham;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dinhDanh == null) ? 0 : dinhDanh.hashCode());
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
		BaocaoDonhangItem other = (BaocaoDonhangItem) obj;
		if (dinhDanh == null) {
			if (other.dinhDanh != null)
				return false;
		} else if (!dinhDanh.equals(other.dinhDanh))
			return false;
		return true;
	}
	public void addSoluongSanpham(int count) {
		this.soLuongSanpham += count;
	}
	
	public void addTongdoanhthu(float grossSales) {
		this.tongDoanhThu += grossSales;
	}
	
	public void addMangluoibanhang(float netSales) {
		this.mangLuoiBanhang += netSales;
	}
	public void increaseOrderCount() {
		this.soLuongDonhang++;
	}
	@Override
	public String toString() {
		return "BaocaoDonhangItem [dinhDanh=" + dinhDanh + ", tongDoanhThu=" + tongDoanhThu + ", mangLuoiBanhang="
				+ mangLuoiBanhang + ", soLuongDonhang=" + soLuongDonhang + ", soLuongSanpham=" + soLuongSanpham + "]";
	}
	
}
