package com.shopme.admin.nguoidung.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.TaiKhoan;

public class NguoiDungPDFExporter extends AbstractExporter{

	public void export(List<TaiKhoan> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/pdf", ".pdf", "nguoidung_");
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		
		Paragraph paragraph = new Paragraph("Danh sách thông tin người dùng", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});
		
		writeTableHeader(table);
		writeTableData(table, listUsers);

		document.add(table);
		
		document.close();
		
		
	}

	private void writeTableData(PdfPTable table, List<TaiKhoan> listUsers) {
		for (TaiKhoan user : listUsers) {
			table.addCell(String.valueOf(user.getMaTK()));
			table.addCell(user.getEmail());
			table.addCell(user.getHo());
			table.addCell(user.getTen());
			table.addCell(user.getPhanquyen().toString());
			table.addCell(String.valueOf(user.isTrangThai()));
		}	
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);		

		cell.setPhrase(new Phrase("Ma TK", font));		
		table.addCell(cell);

		cell.setPhrase(new Phrase("E-mail", font));		
		table.addCell(cell);

		cell.setPhrase(new Phrase("Họ", font));		
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tên", font));		
		table.addCell(cell);		

		cell.setPhrase(new Phrase("Chức vụ ", font));		
		table.addCell(cell);

		cell.setPhrase(new Phrase("Trạng thái", font));		
		table.addCell(cell);
	}
}
