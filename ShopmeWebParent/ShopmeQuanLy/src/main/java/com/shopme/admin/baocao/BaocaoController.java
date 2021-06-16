package com.shopme.admin.baocao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaocaoController {

	@GetMapping("/baocao/banhang")
	public String viewSalesReportHome(Model model) {
		model.addAttribute("pageTitle", "Báo cáo bán hàng");
		return "baocao/baocao_banhang";
	}
}
