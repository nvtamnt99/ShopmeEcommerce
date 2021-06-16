package com.shopme.admin.baocao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.donhang.ChitietDonhangReponsitory;
import com.shopme.common.entity.ChiTietDonHang;

@Service
public class BaocaoChitietDonhangServices {
	@Autowired
	private ChitietDonhangReponsitory repo;

	private String groupBy;
	
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public List<BaocaoDonhangItem> getReportDataLast7Days() {
		return getReportDataLastXDays(7);
	}
	
	public List<BaocaoDonhangItem> getReportDataLast28Days() {
		return getReportDataLastXDays(28);
	}
	
	public List<BaocaoDonhangItem> getReportDataLast6Months() {
		return getReportDataLastXMonths(6);
	}

	public List<BaocaoDonhangItem> getReportDataLastYear() {
		return getReportDataLastXMonths(12);
	}
	private List<BaocaoDonhangItem> getReportDataLastXDays(int days) {
		
		Date endTime = new Date();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(endTime);
		startDate.add(Calendar.DAY_OF_MONTH, -(days - 1));
				
		Date startTime = startDate.getTime();
		
		return getReportDataByDateRange(startTime, endTime);
	}
	
	private List<BaocaoDonhangItem> getReportDataLastXMonths(int months) {
		Date endTime = new Date();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(endTime);
		startDate.add(Calendar.MONTH, -(months - 1));
				
		Date startTime = startDate.getTime();
		
		return getReportDataByDateRange(startTime, endTime);		
	}
	
	private List<BaocaoDonhangItem> getReportDataByDateRange(Date startTime, Date endTime) {
		List<ChiTietDonHang> listOrderDetails = null;
		
		if (groupBy.equals("category")) {
			listOrderDetails = repo.findByChiTietDonHangTenNhanHieuTimeBetween(startTime, endTime);
		} else if (groupBy.equals("product")) {
			System.out.println("Sản phẩm");
			listOrderDetails = repo.findByChiTietDonHangTenSanphamTimeBetween(startTime, endTime);
		}
		
		List<BaocaoDonhangItem> listReportData = new ArrayList<>();

		for (ChiTietDonHang aDetail : listOrderDetails) {
			String identifier = "";
			
			if (groupBy.equals("category")) {
				identifier = aDetail.getSanpham().getNhanhieu().getTen();
			} else if (groupBy.equals("product")) {
				
				identifier = aDetail.getSanpham().getTen();
			}
			
			BaocaoDonhangItem reportItem = new BaocaoDonhangItem(identifier);
			float grossSales = aDetail.getTongphu()  + aDetail.getShip();
			float netSales = aDetail.getTongphu() - aDetail.getChiPhi();
			
			int itemIndex = listReportData.indexOf(reportItem);
			
			if (itemIndex >= 0) {
				BaocaoDonhangItem existingReportItem = listReportData.get(itemIndex);
				existingReportItem.addTongdoanhthu(grossSales);
				existingReportItem.addMangluoibanhang(netSales);
				existingReportItem.addSoluongSanpham(aDetail.getSoLuong());
				
			} else {
				listReportData.add(new BaocaoDonhangItem(identifier, grossSales, netSales, aDetail.getSoLuong()));
			}
			
		}
		
		System.out.println("==== Các mặt hàng báo cáo đơn hàng - " + groupBy + " ===");
		
		for (BaocaoDonhangItem report : listReportData) {
			System.out.println(report);
		}
		
		System.out.println("==== KẾT THÚC Các Mục Báo Cáo Đơn Hàng - " + groupBy + " ===");		
		
		return listReportData;
	}
}
