package com.shopme.admin.sanpham;
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

import com.shopme.common.entity.SanPham;

public class SanPhamExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public SanPhamExcelExporter() {
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

		createCell(row, 0, "Mã sản phẩm", cellStyle);
		createCell(row, 1, "Tên sản phẩm", cellStyle);
		createCell(row, 2, "Bí danh", cellStyle);
		createCell(row, 3, "Thời gian cập nhật", cellStyle);
		createCell(row, 4, "Trạng thái", cellStyle);
		createCell(row, 5, "Tình trạng", cellStyle);
		createCell(row, 6, "Chi phí", cellStyle);
		createCell(row, 7, "Giá bán", cellStyle);
		createCell(row, 8, "Giảm giá %", cellStyle);
		createCell(row, 9, "Chiều dài", cellStyle);
		createCell(row, 10, "Chiều rộng", cellStyle);
		createCell(row, 11, "Chiều cao", cellStyle);
		createCell(row, 12, "Cân nặng", cellStyle);
		createCell(row, 13, "Danh Mục", cellStyle);
		createCell(row, 14, "Nhãn hiệu", cellStyle);
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

	public void export(List<SanPham> listProducts, HttpServletResponse response) throws IOException {
		setResponseHeader(response, "application/octet-stream", ".xlsx", "sanpham_");

		writeHeaderLine();
		writeDataLines(listProducts);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();


	}

	private void writeDataLines(List<SanPham> listProducts) {
		int rowIndex = 1;

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);

		for (SanPham product : listProducts) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			createCell(row, columnIndex++, product.getMaSanPham(), cellStyle);
			createCell(row, columnIndex++, product.getTen(), cellStyle);
			createCell(row, columnIndex++, product.getBiDanh(), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getThoiGianCapNhat()), cellStyle);
			
			if (product.isTrangThai() == true) {
				createCell(row, columnIndex++, String.valueOf("Hoạt động"), cellStyle);
			} else {
				createCell(row, columnIndex++, String.valueOf("Ngưng"), cellStyle);
			}
			
			if (product.isTrongKho() == true) {
				createCell(row, columnIndex++, String.valueOf("Còn hàng"), cellStyle);
			} else {
				createCell(row, columnIndex++, String.valueOf("Hết hàng	"), cellStyle);
			}
			
			createCell(row, columnIndex++, String.valueOf(product.getChiPhi()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getGiaBan()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getChietKhau()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getChieuDai()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getChieuRong()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getChieuRong()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getCanNang()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getDM()), cellStyle);
			createCell(row, columnIndex++, String.valueOf(product.getNH()), cellStyle);
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
