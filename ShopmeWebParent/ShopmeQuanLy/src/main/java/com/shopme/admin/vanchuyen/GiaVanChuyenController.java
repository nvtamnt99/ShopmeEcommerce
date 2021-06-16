package com.shopme.admin.vanchuyen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.GiaVanChuyen;

@Controller
public class GiaVanChuyenController {
	
	@Autowired
	private GiaVanChuyenService service;
	
	@GetMapping("/vanchuyen")
	public String listAll(Model model) {
		return listByPage(model, 1, "datNuoc", "asc", null);
	}
	@GetMapping("/vanchuyen/page/{pageNum}")
	public String listByPage(Model model, 
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {
		
		Page<GiaVanChuyen> page = service.listAll(pageNum, sortField, sortDir, keyword);
		List<GiaVanChuyen> shippingRates = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("shippingRates", shippingRates);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		long startCount = (pageNum - 1) * GiaVanChuyenService.RATES_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + GiaVanChuyenService.RATES_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		if (page.getTotalPages() > 1) {
			model.addAttribute("pageTitle", "Giá vận chuyển (page " + pageNum + ")");
		} else {
			model.addAttribute("pageTitle", "Giá vận chuyển");
		}
		
		return "vanchuyen/gia_vanchuyen";
	}	
	
	@GetMapping("/vanchuyen/new")
	public String newRate(Model model) {
		List<DatNuoc> listCountries = service.getCountryList();
		
		model.addAttribute("rate", new GiaVanChuyen());
		model.addAttribute("pageTitle", "Tạo giá vận chuyển mới");
		model.addAttribute("listCountries", listCountries);
		
		return "vanchuyen/vanchuyen_form";
	}

	@PostMapping("/vanchuyen/save")
	public String saveRate(GiaVanChuyen rate, RedirectAttributes ra) {
		try {
			service.save(rate);
			ra.addFlashAttribute("message", "Phí vận chuyển đã được lưu thành công.");
		} catch (GiaVanChuyenAlreadyExitsException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/vanchuyen";
	}
	
	@GetMapping("/vanchuyen/edit/{id}")
	public String editRate(@PathVariable(name = "id") Integer id,
			Model model, RedirectAttributes ra) {
		try {
			GiaVanChuyen rate = service.get(id);
			List<DatNuoc> listCountries = service.getCountryList();
			
			model.addAttribute("listCountries", listCountries);			
			model.addAttribute("rate", rate);
			model.addAttribute("pageTitle", "Chỉnh sửa phí vận chuyển (ID: " + id + ")");
			
			return "vanchuyen/vanchuyen_form";
		} catch (GiaVanChuyenNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/vanchuyen";
		}
	}
	
	@GetMapping("/vanchuyen/delete/{id}")
	public String deleteRate(@PathVariable(name = "id") Integer id,
			Model model, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message", "Phí vận chuyển có ID " + id + " đã xóa thành công.");
		} catch (GiaVanChuyenNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/vanchuyen";
	}
	
	@GetMapping("/vanchuyen/cod/{id}/{supported}")
	public String updateCODSupport(@PathVariable(name = "id") Integer id,
			@PathVariable(name = "supported") Boolean supported,
			Model model, RedirectAttributes ra) {
		try {
			service.updateCodSupport(id, supported);
			ra.addFlashAttribute("message", "Hỗ trợ COD cho ID phí vận chuyển " + id + " đã được cập nhật.");
		} catch (GiaVanChuyenNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/vanchuyen";
	}	

}
