package com.shopme.admin.donhang;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.caidat.CaidatService;
import com.shopme.admin.sanpham.SanPhamService;
import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.ChiTietDonHang;
import com.shopme.common.entity.DonHang;
import com.shopme.common.entity.SanPham;
import com.shopme.common.entity.TheoDoiDonHang;
import com.shopme.common.entity.TinhTrangDonHang;

@Controller
public class DonhangController {

	@Autowired
	private DonhangService orderService;
	
	@Autowired
	private SanPhamService productService;
	
	@Autowired
	private CaidatService settingService;
	
	@GetMapping("/donhang")
	public String listAll(Model model, HttpServletRequest request) {
		return listByPage(model, request, 1, "thoiGianDatHang", "desc", null);
	}
	
	@GetMapping("/donhang/page/{pageNum}")
	public String listByPage(Model model, HttpServletRequest request,
						@PathVariable(name = "pageNum") int pageNum,
						@Param("sortField") String sortField,
						@Param("sortDir") String sortDir,
						@Param("keyword") String keyword
			) {
		
		Page<DonHang> page = orderService.listAll(pageNum, sortField, sortDir, keyword);
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
			model.addAttribute("pageTitle", "Orders (page " + pageNum + ")");
		} else {
			model.addAttribute("pageTitle", "Orders");
		}
		
		loadCurrencySetting(request);
		
		return "donhang/donhang";
	}	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<CaiDat> currencySettings = settingService.getCurrencySettings();
		
		for (CaiDat setting : currencySettings) {
			request.setAttribute(setting.getTuKhoa(), setting.getGiaTri());
		}	
	}
	@GetMapping("/donhang/detail/{id}")
	public String viewOrder(@PathVariable("id") Integer id, Model model, 
			RedirectAttributes ra,
			HttpServletRequest request) {
		
		try {
			DonHang order = orderService.get(id);
			loadCurrencySetting(request);
			model.addAttribute("order", order);
			return "donhang/donhang_detail_modal";
		} catch (DonhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/donhang";
		}
		
	}
	@GetMapping("/donhang/delete/{id}")
	public String deleteOrder(@PathVariable("id") Integer id, 
			Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		try {
			orderService.delete(id);;
			loadCurrencySetting(request);
			
			ra.addFlashAttribute("message", "Đơn hàng có mã " + id + " đã được xóa.");
			
		} catch (DonhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/donhang";
		
	}
	@GetMapping("/donhang/edit/{id}")
	public String editOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		try {
			DonHang order = orderService.get(id);;
			
			model.addAttribute("pageTitle", "Chỉnh sửa Đơn hàng (ID: " + id + ")");
			model.addAttribute("order", order);
			
			return "donhang/donhang_form";
			
		} catch (DonhangNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/donhang";
		}
		
	}
	@GetMapping("/donhang/search_product")
	public String showSearchProductPage(Model model,
			HttpServletResponse response) {
		return "donhang/search_product";
	}
	@PostMapping("/donhang/search_product")
	public String searchProducts(Model model, @Param("keyword") String keyword) {
		return searchProductsByPage(model, 1, keyword);
	}
	
	@GetMapping("/donhang/search_product/page/{pageNum}")
	public String searchProductsByPage(Model model,
			@PathVariable(name = "pageNum") int pageNum,
			@Param("keyword") String keyword) {

		Page<SanPham> page = productService.searchProducts(pageNum, keyword);
		List<SanPham> listProducts = page.getContent();
		
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("keyword", keyword);
		
		long startCount = (pageNum - 1) * SanPhamService.PRODUCTS_PER_PAGE + 1;
		model.addAttribute("startCount", startCount);
		
		long endCount = startCount + SanPhamService.PRODUCTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("endCount", endCount);
		
		return "donhang/search_product";
	}
	@PostMapping("/donhang/save")
	public String saveOrder(DonHang order, HttpServletRequest request, RedirectAttributes ra)
			throws ParseException {
		printOrderDetailsForDebugging(order, request);		
		
		updateOrderDetails(order, request);
		updateTrackDetails(order, request);
		
		orderService.save(order);
		
		ra.addFlashAttribute("message", "Đơn hàng có mã "
				+ order.getMaDonHang() + " được cập nhật thành công.");
		
		return "redirect:/donhang";
	}

	private void printOrderDetailsForDebugging(DonHang order, HttpServletRequest request) {
		String deliverDateOnForm = order.getDeliverDateOnForm();
		System.out.println("Order ID: " + order.getMaDonHang());
		System.out.println("Subtotal: " + order.getTongPhu());
		System.out.println("Shipping Cost: " + order.getGiaVanChuyen());
		System.out.println("Tax: " + order.getThue());
		System.out.println("Total: " + order.getTong());		
		System.out.println("deliverDateOnForm: " + deliverDateOnForm);
		
		String[] detailIds = request.getParameterValues("detailId");
		String[] productIds = request.getParameterValues("productId");
		String[] quantities = request.getParameterValues("quantity");
		String[] prices = request.getParameterValues("price");
		String[] costs = request.getParameterValues("productCost");
		String[] ships = request.getParameterValues("productShip");		
		String[] subtotals = request.getParameterValues("productSubtotal");
		
		System.out.println("-- Detail IDs --");
		for (String did : detailIds) {
			System.out.print(did + " - ");
		}
		
		System.out.println("\n-- Product IDs --");
		for (String pid : productIds) {
			System.out.print(pid + " - ");
		}
		
		System.out.println("\n-- Product Quantities --");
		for (String pqty : quantities) {
			System.out.print(pqty + " - ");
		}		
		
		System.out.println("\n-- Product Prices --");
		for (String pprice : prices) {
			System.out.print(pprice + " - ");
		}
		
		System.out.println("\n-- Product Costs --");
		for (String pcost : costs) {
			System.out.print(pcost + " - ");
		}

		System.out.println("\n-- Product Ships --");
		for (String pship : ships) {
			System.out.print(pship + " - ");
		}
		
		System.out.println("\n-- Product Subtotals --");
		for (String psubtotal : subtotals) {
			System.out.print(psubtotal + " - ");
		}		
		
		String[] trackIds = request.getParameterValues("trackId");
		String[] trackDates = request.getParameterValues("trackDate");
		String[] trackStatuses = request.getParameterValues("trackStatus");
		String[] trackNotes = request.getParameterValues("trackNotes");
		
		System.out.println("\n-- Track IDs --");
		for (String tid : trackIds) {
			System.out.print(tid + " - ");
		}
		
		System.out.println("\n-- Track Dates --");
		for (String tdate : trackDates) {
			System.out.print(tdate + " - ");
		}
		
		System.out.println("\n-- Track Statuses --");
		for (String tstatus : trackStatuses) {
			System.out.print(tstatus + " - ");
		}		
		
		System.out.println("\n-- Track Notes --");
		for (String tnote : trackNotes) {
			System.out.print(tnote + " - ");
		}
	}
	
	private void updateOrderDetails(DonHang order, HttpServletRequest request) {
		Set<ChiTietDonHang> orderDetails = order.getChiTietDH();
		
		String[] detailIds = request.getParameterValues("detailId");
		String[] productIds = request.getParameterValues("productId");
		String[] quantities = request.getParameterValues("quantity");
		String[] prices = request.getParameterValues("price");
		String[] costs = request.getParameterValues("productCost");
		String[] ships = request.getParameterValues("productShip");
		String[] subtotals = request.getParameterValues("productSubtotal");
		
		for (int i = 0; i < productIds.length; i++) {
			ChiTietDonHang aDetail = new ChiTietDonHang();

			int orderDetailId = Integer.parseInt(detailIds[i]);
			if (orderDetailId > 0) {
				aDetail.setMaChiTietDonHang(orderDetailId);
			}
			
			aDetail.setDonhang(order);
			aDetail.setSanpham(new SanPham(Integer.parseInt(productIds[i])));
			aDetail.setChiPhi(Float.parseFloat(costs[i]));
			aDetail.setShip(Float.parseFloat(ships[i]));
			aDetail.setSoLuong(Integer.parseInt(quantities[i]));
			aDetail.setDonGia(Float.parseFloat(prices[i]));
			aDetail.setTongphu(Float.parseFloat(subtotals[i]));
			
			orderDetails.add(aDetail);
		}		
	}
	
	private void updateTrackDetails(DonHang order, HttpServletRequest request) throws ParseException {
		List<TheoDoiDonHang> orderTracks = order.getTheoDoiDH();
		
		String[] trackIds = request.getParameterValues("trackId");
		String[] trackDates = request.getParameterValues("trackDate");
		String[] trackStatuses = request.getParameterValues("trackStatus");
		String[] trackNotes = request.getParameterValues("trackNotes");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		for (int i = 0; i < trackIds.length; i++) {
			TheoDoiDonHang aTrack = new TheoDoiDonHang();
			int trackId = Integer.parseInt(trackIds[i]);
			if (trackId > 0) {
				aTrack.setMaTheoDoiDH(trackId);
			}
			aTrack.setDonhang(order);
			aTrack.setThoigian_capnhat(dateFormatter.parse(trackDates[i]));
			aTrack.setTinhTrangDonHang(TinhTrangDonHang.valueOf(trackStatuses[i]));
			aTrack.setChuThich(trackNotes[i]);
			
			orderTracks.add(aTrack);
		}
	}
	@GetMapping("/donhang/export/excel")
	public void xuatFileExcel(HttpServletResponse response) throws IOException {
		List<DonHang> listUsers = orderService.listAll();
		
		DonHangExcelExporter exporter = new DonHangExcelExporter();
		exporter.export(listUsers, response);
	}
}
