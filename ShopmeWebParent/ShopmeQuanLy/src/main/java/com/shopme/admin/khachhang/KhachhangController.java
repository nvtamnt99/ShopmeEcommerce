package com.shopme.admin.khachhang;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;

@Controller
public class KhachhangController {

	@Autowired
	private KhachhangService service;
	
	@GetMapping("/khachhang")
	public String listAll(Model model) {
		return listByPage(model, 1, "ho", "asc", null);
	}

	@GetMapping("/khachhang/page/{pageNum}")
	public String listByPage(Model model, 
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {
		
		Page<KhachHang> page = service.listAll(pageNum, sortField, sortDir, keyword);
		List<KhachHang> listUsers = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listCustomers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		long startCount = (pageNum - 1) * KhachhangService.CUSTOMERS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + KhachhangService.CUSTOMERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		if (page.getTotalPages() > 1) {
			model.addAttribute("pageTitle", "Customers (page " + pageNum + ")");
		} else {
			model.addAttribute("pageTitle", "Customers");
		}
		
		return "khachhang/khachhang";
	}
	
	@GetMapping("/khachhang/{id}/enabled/{status}")
	public RedirectView updateCustomerEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes ra) {
		service.updateCustomerEnabledStatus(id, enabled);
		RedirectView rv = new RedirectView("/khachhang", true);
		String status = enabled ? "enabled" : "disabled";
		ra.addFlashAttribute("message", String.format("ID khách hàng %d là %s.", id, status));
		return rv;
	}
	
	@GetMapping("/khachhang/detail/{id}")
	public String viewCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			KhachHang customer = service.get(id);
			model.addAttribute("customer", customer);
			
			return "khachhang/khachhang_detail_modal";
			
		} catch (KhachhangNotFoundException ex) {
			ra.addFlashAttribute("message", "Không thể tìm thấy bất kỳ khách hàng nào với ID " + id);
			return "redirect:/khachhang";			
		}
	}
	@GetMapping("/khachhang/edit/{id}")
	public String editCustomer(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			KhachHang customer = service.get(id);
			List<DatNuoc> countries = service.listAllCountries();
			model.addAttribute("listCountries", countries);
			
			model.addAttribute("customer", customer);
			model.addAttribute("pageTitle", String.format("Chỉnh sửa khách hàng (ID: %d)", id));
			
			return "khachhang/khachhang_form";
			
		} catch (KhachhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/khachhang";
		}
	}
	@PostMapping("/khachhang/save")
	public String saveCustomer(KhachHang customer, Model model, RedirectAttributes ra) throws KhachhangNotFoundException {
		if (!customer.getMatKhau().isEmpty()) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(customer.getMatKhau());
			customer.setMatKhau(encodedPassword);			
		} else {
			KhachHang existingCustomer = service.get(customer.getMaKhachHang());
			customer.setMatKhau(existingCustomer.getMatKhau());
		}
		
		service.save(customer);
		ra.addFlashAttribute("message", "Khách hàng có mã " + customer.getMaKhachHang() + " lưu thành công.");
		return "redirect:/khachhang";
	}
	@GetMapping("/khachhang/delete/{id}")
	public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			service.delete(id);			
			ra.addFlashAttribute("message", " Khách hàng có mã " + id + " đã được xóa thành công.");
			
		} catch (KhachhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/khachhang";
	}
	@GetMapping("/khachhang/export/csv")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=customers_" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);
		
		List<KhachHang> customers = service.listAll();
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = {"Mã khách hàng", "Họ Tên", "E-mail", "Số điện thoại", "Địa chỉ 1", "Địa chỉ 2", "Thành phố", "Tỉnh", "Quốc gia", "Mã bưu điện", "Trạng thái", "Thời gian tạo"};
		String[] nameMapping = {"maKhachHang", "HoTen", "email", "soDienThoai", "diaChi1", "diaChi2", "thanhPho", "tinh", "datNuoc", "maBuuDien", "trangThai", "thoiGianTao"};
		
		csvWriter.writeHeader(csvHeader);		

		for (KhachHang customer : customers) {
			csvWriter.write(customer, nameMapping);
		}
		
		csvWriter.close();
	}
	@GetMapping("/khachhang/export/excel")
	public void xuatFileExcel(HttpServletResponse response) throws IOException {
		List<KhachHang> listUsers = service.listAll();
		
		KhachhangExcelExporter exporter = new KhachhangExcelExporter();
		exporter.export(listUsers, response);
	}
}
