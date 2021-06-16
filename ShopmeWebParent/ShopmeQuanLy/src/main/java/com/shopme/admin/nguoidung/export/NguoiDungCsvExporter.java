package com.shopme.admin.nguoidung.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.TaiKhoan;

public class NguoiDungCsvExporter extends AbstractExporter{
	
	public void export(List<TaiKhoan> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "nguoidung_");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
				CsvPreference.STANDARD_PREFERENCE);
		
		String [] csvHeader = {"Ma TK", "E-mail", "Ho", "Ten", "Chuc Vu", "Trang Thai"};
		String [] fieldMapping = {"maTK", "email", "ho", "ten", "phanquyen", "trangThai"};
		
		csvWriter.writeHeader(csvHeader);
		
		for (TaiKhoan user : listUsers) {
			csvWriter.write(user, fieldMapping);
		}
		
		csvWriter.close();
	}

}
