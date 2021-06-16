package com.shopme.admin.donhang;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.DonHang;

public class DonHangExcelExporter extends AbstractExporter{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public DonHangExcelExporter() {
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("KhachHang");
		XSSFRow row = sheet.createRow(0);
		
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "Mã đơn hàng", cellStyle);
		createCell(row, 1, "Họ", cellStyle);
		createCell(row, 2, "Tên", cellStyle);
		createCell(row, 3, "Địa chỉ", cellStyle);
		createCell(row, 4, "Quốc gia", cellStyle);
		createCell(row, 5, "Thời gian đặt hàng", cellStyle);
		createCell(row, 6, "Phương thức thanh toán", cellStyle);
		createCell(row, 7, "Giá vận chuyển", cellStyle);
		createCell(row, 8, "Chi phí", cellStyle);
		createCell(row, 9, "Thuế", cellStyle);
		createCell(row, 10, "Tổng phụ", cellStyle);
		createCell(row, 11, "Tổng", cellStyle);
		createCell(row, 12, "Tình trạng đơn hàng", cellStyle);
		createCell(row, 13, "Số ngày vận chuyển", cellStyle);
		createCell(row, 14, "Ngày giao hàng", cellStyle);
		createCell(row, 15, "Khách hàng", cellStyle);
		
	}
	
	private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(style);
	}
	
	public void export(List<DonHang> listDonhang, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "donhang_");
	
		writeHeaderLine();
		writeDataLines(listDonhang);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

	private void writeDataLines(List<DonHang> listDonhang) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for (DonHang dh : listDonhang) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			createCell(row, columnIndex++, dh.getMaDonHang(), cellStyle);
			createCell(row, columnIndex++, dh.getHo(), cellStyle);
			createCell(row, columnIndex++, dh.getTen(), cellStyle);
			createCell(row, columnIndex++, dh.getShippingAddress(), cellStyle);
			createCell(row, columnIndex++, dh.getQuocGia().toString(), cellStyle);
			createCell(row, columnIndex++, dh.getNgaytao(), cellStyle);
			createCell(row, columnIndex++, dh.getPhuongThucThanhToan().toString(), cellStyle);
			createCell(row, columnIndex++, String.valueOf(dh.getGiaVanChuyen()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(dh.getChiPhi()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(dh.getThue()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(dh.getTongPhu()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(dh.getTong()), cellStyle);
			createCell(row, columnIndex++, dh.getTinhTrangDH().toString(), cellStyle);
			createCell(row, columnIndex++, String.valueOf(dh.getGiaoNgay()), cellStyle);
			createCell(row, columnIndex++, dh.getNgayGiao(), cellStyle);
			createCell(row, columnIndex++, dh.getKhachHang().getHoTen(), cellStyle);
			
			
			
		}
		
	}
}
