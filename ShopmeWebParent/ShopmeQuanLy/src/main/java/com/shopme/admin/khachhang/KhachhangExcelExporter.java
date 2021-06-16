package com.shopme.admin.khachhang;

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
import com.shopme.common.entity.KhachHang;

public class KhachhangExcelExporter extends AbstractExporter{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public KhachhangExcelExporter() {
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
		
		createCell(row, 0, "Mã Khách hàng", cellStyle);
		createCell(row, 1, "E-mail", cellStyle);
		createCell(row, 2, "Họ", cellStyle);
		createCell(row, 3, "Tên", cellStyle);
		createCell(row, 4, "Thời gian tạo", cellStyle);
		createCell(row, 5, "Trạng thái", cellStyle);
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
	
	public void export(List<KhachHang> listKhachhang, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "khachhang_");
	
		writeHeaderLine();
		writeDataLines(listKhachhang);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

	private void writeDataLines(List<KhachHang> listKhachhang) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for (KhachHang kh : listKhachhang) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			
			createCell(row, columnIndex++, kh.getMaKhachHang(), cellStyle);
			createCell(row, columnIndex++, kh.getEmail(), cellStyle);
			createCell(row, columnIndex++, kh.getHo(), cellStyle);
			createCell(row, columnIndex++, kh.getTen(), cellStyle);
			createCell(row, columnIndex++, kh.getNgaytao(), cellStyle);
			createCell(row, columnIndex++, kh.isTrangThai(), cellStyle);
			
			
		}
		
	}
}
