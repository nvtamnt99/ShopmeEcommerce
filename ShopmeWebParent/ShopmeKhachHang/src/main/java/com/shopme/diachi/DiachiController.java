package com.shopme.diachi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.Tienich;
import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.KhachHang;
import com.shopme.khachhang.KhachHangService;

@Controller
public class DiachiController {

	@Autowired
	private DiachiService addressService;

	@Autowired
	private KhachHangService customerService;

	@GetMapping("/khachhang/diachi")
	public String listAddresses(Model model, HttpServletRequest request) {
		KhachHang customer = getAuthenticatedCustomer(request);
		List<DiaChi> listAddresses = addressService.listAddressesOf(customer);
		DiaChi defaultAddress = addressService.getDefaultAddressOf(customer);
		Integer defaultAddressId = 0; // customer's address

		if (defaultAddress != null) {
			defaultAddressId = defaultAddress.getMa();
		}
		model.addAttribute("listAddresses", listAddresses);
		model.addAttribute("defaultAddressId", defaultAddressId);
		model.addAttribute("customer", customer);
		model.addAttribute("pageTitle", "Chọn một địa chỉ giao hàng");

		return "diachi/diachi";
	}

	@PostMapping("/khachhang/diachi/save")
	public String saveAddress(DiaChi address, HttpServletRequest request) {
		KhachHang customer = getAuthenticatedCustomer(request);
		String redirectOption = request.getParameter("redirect");
		String redirectURL = "redirect:/khachhang/diachi";

		address.setKhachHang(customer);

		if ("checkout".equals(redirectOption)) {
			address.setDefaultSelection(true);
			redirectURL = "redirect:/checkout";
		}

		addressService.save(address, customer);

		return redirectURL;

	}

	@GetMapping("/khachhang/diachi/chon/{id}")
	public String chooseDefaultAddress(@PathVariable(name = "id") Integer addressId, HttpServletRequest request) {
		KhachHang customer = getAuthenticatedCustomer(request);
		addressService.setDefaultAddress(addressId, customer);

		String redirectOption = request.getParameter("redirect");

		if ("checkout".equals(redirectOption)) {
			return "redirect:/checkout";
		}

		return "redirect:/khachhang/diachi";
	}

	@GetMapping("/khachhang/diachi/new")
	public String newAddress(Model model, HttpServletRequest request) {
		KhachHang customer = getAuthenticatedCustomer(request);
		List<DatNuoc> countries = customerService.listAllCountries();

		model.addAttribute("listCountries", countries);

		model.addAttribute("customer", customer);
		model.addAttribute("pageTitle", "Tạo địa chỉ mới");
		model.addAttribute("address", new DiaChi());

		return "diachi/diachi_form";
	}

	@GetMapping("/khachhang/diachi/edit/{id}")
	public String editAddress(@PathVariable(name = "id") Integer addressId, Model model,
			HttpServletRequest request) {
		KhachHang customer = getAuthenticatedCustomer(request);
		DiaChi address = addressService.get(addressId, customer);

		if (address == null) {
			model.addAttribute("title", "Chỉnh sửa địa chỉ");
			model.addAttribute("message", "Không thể tìm thấy bất kỳ địa chỉ nào có ID " + addressId);
			return "message";
		}

		List<DatNuoc> countries = customerService.listAllCountries();

		model.addAttribute("listCountries", countries);
		model.addAttribute("address", address);
		model.addAttribute("pageTitle", "Chỉnh sửa địa chỉ (ID: " + addressId + ")");

		return "diachi/diachi_form";
	}

	@GetMapping("/diachi/delete/{id}")
	public String deleteAddress(@PathVariable(name = "id") Integer addressId, Model model,
			HttpServletRequest request) {

		KhachHang customer = getAuthenticatedCustomer(request);
		addressService.delete(addressId, customer);

		return "redirect:/khachhang/diachi";
	}

	private KhachHang getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Tienich.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
}
