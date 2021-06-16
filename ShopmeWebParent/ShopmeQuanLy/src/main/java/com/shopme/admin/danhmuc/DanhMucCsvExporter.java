package com.shopme.admin.danhmuc;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.DanhMuc;
public class DanhMucCsvExporter extends AbstractExporter {
	public void export(List<DanhMuc> listCategories, HttpServletResponse response) 
			throws IOException {
		super.setResponseHeader(response, "text/csv", ".csv", "danhmuc_");

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), 
				CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = {"Mã danh mục", "tên danh mục"};
		String[] fieldMapping = {"maDanhMuc", "ten"};

		csvWriter.writeHeader(csvHeader);

		for (DanhMuc category : listCategories) {
			category.setTen(category.getTen().replace("--", "  "));
			csvWriter.write(category, fieldMapping);
		}

		csvWriter.close();
	}
	
}
