package com.shopme.admin.danhmuc;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.shopme.common.entity.DanhMuc;

public class DanhMucExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public DanhMucExcelExporter() {
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("SanPham");
		XSSFRow row = sheet.createRow(0);

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);

		createCell(row, 0, "Mã danh mục", cellStyle);
		createCell(row, 1, "Tên danh mục", cellStyle);
		createCell(row, 2, "Bí danh", cellStyle);
		createCell(row, 3, "Trạng thái", cellStyle);
		createCell(row, 4, "Danh mục cha", cellStyle);
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

	public void export(List<DanhMuc> listCategories, HttpServletResponse response) throws IOException {
		setResponseHeader(response, "application/octet-stream", ".xlsx", "danhmuc_");

		writeHeaderLine();
		writeDataLines(listCategories);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();


	}

	private void writeDataLines(List<DanhMuc> listCategories) {
		int rowIndex = 1;

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);

		for (DanhMuc category : listCategories) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			createCell(row, columnIndex++, category.getMaDanhMuc(), cellStyle);
			createCell(row, columnIndex++, category.getTen(), cellStyle);
			createCell(row, columnIndex++, category.getBiDanh(), cellStyle);	
			
			if (category.isTrangThai() == true) {
				createCell(row, columnIndex++, String.valueOf("Hoạt động"), cellStyle);
			} else {
				createCell(row, columnIndex++, String.valueOf("Ngưng"), cellStyle);
			}
			
			createCell(row, columnIndex++, String.valueOf(category.getDanhMucCha()), cellStyle);
		}
	}

	private void setResponseHeader(HttpServletResponse response, String contentType,
			String extension, String prefix) throws IOException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormatter.format(new Date());
		String fileName = prefix + timestamp + extension;

		response.setContentType(contentType);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
	}
}
