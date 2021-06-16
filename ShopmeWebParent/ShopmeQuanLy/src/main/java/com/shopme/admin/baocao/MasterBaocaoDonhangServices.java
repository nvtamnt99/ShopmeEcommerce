package com.shopme.admin.baocao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.donhang.DonhangReponsitory;
import com.shopme.common.entity.DonHang;

@Service
public class MasterBaocaoDonhangServices {
	private DateFormat dateFormatter;
	@Autowired private DonhangReponsitory orderRepo;
	
	public List<BaocaoDonhangItem> listSalesReportLast28Days() {
		return listSalesReportLastXDays(28);
	}
	
	public List<BaocaoDonhangItem> listSalesReportLast7Days() {
		return listSalesReportLastXDays(7);
	}

	private List<BaocaoDonhangItem> listSalesReportLastXDays(int days) {
		
		Date endTime = new Date();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(endTime);
		startDate.add(Calendar.DAY_OF_MONTH, -(days - 1));
				
		Date startTime = startDate.getTime();
		
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				
		return listOrderReportByDateRange(startTime, endTime, "days");
	}

	public List<BaocaoDonhangItem> listSalesReportLastYear() {
		return listSalesReportLastXMonths(12);
	}
	
	public List<BaocaoDonhangItem> listSalesReportLast6Months() {
		return listSalesReportLastXMonths(6);
	}
	
	private List<BaocaoDonhangItem> listSalesReportLastXMonths(int months) {
		System.out.println("listSalesReportLast6Months");
		
		Date endTime = new Date();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(endTime);
		startDate.add(Calendar.MONTH, -(months - 1));
				
		Date startTime = startDate.getTime();
		
		System.out.println("Start time: " + startTime);
		System.out.println("End time: " + endTime);
		
		dateFormatter = new SimpleDateFormat("yyyy-MM");
				
		return listOrderReportByDateRange(startTime, endTime, "months");		
	}
	
	private List<BaocaoDonhangItem> listOrderReportByDateRange(Date startTime, Date endTime, String periodType) {
				
		List<DonHang> listOrders = orderRepo.findBythoiGianDatHangBetween(startTime, endTime);
		
		printRawOrderList(listOrders);
		
		List<BaocaoDonhangItem> listReportItems = createOrderReportList(startTime, endTime, periodType);
		updateOrderReportList(listOrders, listReportItems);
		
		printReportItemsList(listReportItems);
		
		return listReportItems;
	}

	private void printRawOrderList(List<DonHang> listOrders) {
		System.out.println("==== Danh sách đơn hàng thô trước khi xử lý ===");
		
		int count = 1;		
		for (DonHang order : listOrders) {			
			System.out.println(count++ + " | " + order.getMaDonHang() + " | " + order.getThoiGianDatHang()
				+ " | " + order.getTong() + " | " + order.getChiPhi());		
		}
		
		System.out.println("==== KẾT THÚC Danh sách đơn hàng thô trước khi xử lý ===");
	}
	
	private void updateOrderReportList(List<DonHang> listOrders, List<BaocaoDonhangItem> listReportItems) {
		for (DonHang order : listOrders) {			
			String orderDateOnly = dateFormatter.format(order.getThoiGianDatHang());
			BaocaoDonhangItem reportItem = new BaocaoDonhangItem(orderDateOnly);
			
			int itemIndex = listReportItems.indexOf(reportItem);
			
			if (itemIndex >= 0) {
				reportItem = listReportItems.get(itemIndex);
				
				reportItem.addTongdoanhthu(order.getTong());
				reportItem.addMangluoibanhang(order.getTongPhu() - order.getChiPhi());
				reportItem.increaseOrderCount();
			}
		}	
		
	}

	private void printReportItemsList(List<BaocaoDonhangItem> listReportItems) {
		System.out.println("==== Các mặt hàng báo cáo đơn hàng ===");
		
		for (BaocaoDonhangItem report : listReportItems) {
			System.out.println(report);
		}
		
		System.out.println("==== KẾT THÚC Các Mục Báo Cáo Đơn Hàng ===");
	}
	
	private List<BaocaoDonhangItem> createOrderReportList(Date startTime, Date endTime, String periodType) {
		List<BaocaoDonhangItem> listReportItems = new ArrayList<>();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startTime);
		
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(endTime);		
		
		Date currentDate = startDate.getTime();
		String currentDateOnly = dateFormatter.format(currentDate);
		
		listReportItems.add(new BaocaoDonhangItem(currentDateOnly));
		
		do {
			if (periodType.equals("days")) {
				startDate.add(Calendar.DAY_OF_MONTH, 1);
			} else if (periodType.equals("months")) {
				startDate.add(Calendar.MONTH, 1);
			}
			
			currentDate = startDate.getTime();
			currentDateOnly = dateFormatter.format(currentDate);
			
			listReportItems.add(new BaocaoDonhangItem(currentDateOnly));
			
		} while (startDate.before(endDate));

		return listReportItems;
	}
}
