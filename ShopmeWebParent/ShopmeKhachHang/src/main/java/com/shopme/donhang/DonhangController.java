package com.shopme.donhang;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.Tienich;
import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.TheoDoiDonHang;
import com.shopme.common.entity.TinhTrangDonHang;
import com.shopme.khachhang.KhachHangService;

@Controller
public class DonhangController {
	
	@Autowired private DonhangService orderService;
	
	@Autowired private KhachHangService khachHangService;

	@GetMapping("/khachhang/donhang")
	public String listOrders(Model model, HttpServletRequest request) {
		return listOrdersByPage(model, request, 1, "thoiGianDatHang", "desc", null);
	}
	@GetMapping("/khachhang/donhang/page/{pageNum}")
	public String listOrdersByPage(Model model,			
						HttpServletRequest request,
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {
		
		KhachHang customer = getAuthenticatedCustomer(request);
		
		Page<DonHang> page = orderService.getOrdersForCustomer(
								customer, pageNum, sortField, sortDir, keyword);
		List<DonHang> listOrders = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("keyword", keyword);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		long startCount = (pageNum - 1) * DonhangService.ORDERS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + DonhangService.ORDERS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		if (page.getTotalPages() > 1) {
			model.addAttribute("pageTitle", "Đơn đặt hàng của tôi (page " + pageNum + ")");
		} else {
			model.addAttribute("pageTitle", "Đơn hàng của tôi");
		}
		
		return "donhang/donhang";		
	}
	@GetMapping("/khachhang/donhang/detail/{id}")
	public String viewOrderDetails(Model model, HttpServletRequest request,
			@PathVariable(name = "id") Integer id) {
		
		KhachHang customer = getAuthenticatedCustomer(request);
		
		DonHang order = orderService.getOrderDetails(id, customer);
		
		model.addAttribute("pageTitle", "Chi tiết đơn hàng");
		model.addAttribute("order", order);
		
		return "donhang/order_detail_modal";
	}	
	@GetMapping("/donhang/delete/{id}")
	public String deleteOrder(
			@PathVariable(name = "id") Integer id, 
			Model model, RedirectAttributes ra, HttpServletRequest request) {
		KhachHang customer = getAuthenticatedCustomer(request);
		DonHang order = orderService.getOrderDetails(id, customer);
		order.setTinhTrangDH(TinhTrangDonHang.HUY);
		TheoDoiDonHang firstDonhang = new TheoDoiDonHang();
		firstDonhang.setDonhang(order);
		firstDonhang.setTinhTrangDonHang(TinhTrangDonHang.HUY);
		firstDonhang.setThoigian_capnhat(new Date());
		firstDonhang.setChuThich(TinhTrangDonHang.HUY.getDescription());
		
		order.getTheoDoiDH().add(firstDonhang);
				
		orderService.saveDonhang(order);
		
		return "redirect:/khachhang/donhang";
	}
	private KhachHang getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Tienich.getEmailOfAuthenticatedCustomer(request);				
		return khachHangService.getCustomerByEmail(email);
	}	
	
}
