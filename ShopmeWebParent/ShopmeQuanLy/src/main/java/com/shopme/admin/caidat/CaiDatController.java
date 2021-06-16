package com.shopme.admin.caidat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TienTe;

@Controller
public class CaiDatController {

	@Autowired private CaidatService caidatService;
	
	@Autowired private CurrencyReponsitory tienTeRepository;
	
	@GetMapping("/caidat")
	public String listAll(Model model) {
		List<CaiDat> listSettings = caidatService.listAllSettings();
		List<TienTe> listCurrencies = (List<TienTe>) tienTeRepository.findAll();
		
		model.addAttribute("dsDonvi", listCurrencies);
		
		for (CaiDat setting : listSettings) {
			model.addAttribute(setting.getTuKhoa(), setting.getGiaTri());
		}
		
		return "caidat/caidat";
	}
	
	@PostMapping("/caidat/save_general")
	public String saveGeneralSettings(@RequestParam("fileImage") MultipartFile multipartFile,
			HttpServletRequest request, RedirectAttributes ra) throws IOException {
		GeneralSettingBag settingBag = caidatService.getGeneralSettings();
		
		saveSiteLogo(multipartFile, settingBag);
		saveCurrencySymbol(request, settingBag);
		
		updateSettingValuesFromForm(request, settingBag.list());
		
		ra.addFlashAttribute("message", "Cài đặt chung đã được lưu.");
		
		return "redirect:/caidat";
	}
	private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String value = "/site-logo/" + fileName;
			settingBag.updateSiteLogo(value);
			
			String uploadDir = "../site-logo/";
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
	}
	private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag settingBag) {
		Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
		Optional<TienTe> findByIdResult = tienTeRepository.findById(currencyId);
		
		if (findByIdResult.isPresent()) {
			TienTe currency = findByIdResult.get();
			settingBag.updateCurrencySymbol(currency.getBieuTuong());
		}
	}
	private void updateSettingValuesFromForm(HttpServletRequest request, List<CaiDat> listSettings) {
		for (CaiDat setting : listSettings) {
			String value = request.getParameter(setting.getTuKhoa());
			if (value != null) {
				setting.setGiaTri(value);
			}
		}
		
		caidatService.saveAll(listSettings);
	}
	@PostMapping("/caidat/save_mail_server")
	public String saveMailServerSetttings(HttpServletRequest request, RedirectAttributes ra) {
		List<CaiDat> mailServerSettings = caidatService.getMailServerSettings();
		updateSettingValuesFromForm(request, mailServerSettings);
		
		ra.addFlashAttribute("message", "Cài đặt máy chủ thư đã được lưu");
		
		return "redirect:/caidat#mailServer";
	}
	@PostMapping("/caidat/save_mail_templates")
	public String saveMailTemplateSetttings(HttpServletRequest request, RedirectAttributes ra) {
		List<CaiDat> mailTemplateSettings = caidatService.getMailTemplateSettings();
		updateSettingValuesFromForm(request, mailTemplateSettings);
		
		ra.addFlashAttribute("message", "Cài đặt mẫu thư đã được lưu");
		
		return "redirect:/caidat";
	}	
	@PostMapping("/caidat/save_payment")
	public String savePayment(HttpServletRequest request, RedirectAttributes ra) throws IOException {
		List<CaiDat> paymentSettings = caidatService.getPaymentSettings();
		
		updateSettings(paymentSettings, request);
		
		ra.addFlashAttribute("message", "Cài đặt thanh toán đã được lưu.");
		
		return "redirect:/caidat";
	}	
	
	private void updateSettings(List<CaiDat> listSettings, HttpServletRequest request) {
		for (CaiDat setting : listSettings) {
			String value = request.getParameter(setting.getTuKhoa());
			if (value != null) {
				setting.setGiaTri(value);
			}			
		}	
		
		caidatService.saveAll(listSettings);		
	}
}
