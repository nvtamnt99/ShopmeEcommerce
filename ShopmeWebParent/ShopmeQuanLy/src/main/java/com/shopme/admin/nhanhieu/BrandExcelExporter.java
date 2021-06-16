package com.shopme.admin.nhanhieu;
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

import com.shopme.common.entity.NhanHieu;

public class BrandExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public BrandExcelExporter() {
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("NhanHieu");
		XSSFRow row = sheet.createRow(0);

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);

		createCell(row, 0, "Mã nhãn hiệu", cellStyle);
		createCell(row, 1, "Tên nhãn hiệu", cellStyle);
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

	public void export(List<NhanHieu> listBrands, HttpServletResponse response) throws IOException {
		setResponseHeader(response, "application/octet-stream", ".xlsx", "nhanhieu_");

		writeHeaderLine();
		writeDataLines(listBrands);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();


	}

	private void writeDataLines(List<NhanHieu> listBrands) {
		int rowIndex = 1;

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);

		for (NhanHieu brand : listBrands) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			createCell(row, columnIndex++, brand.getMaNhanHieu(), cellStyle);
			createCell(row, columnIndex++, brand.getTen(), cellStyle);
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
