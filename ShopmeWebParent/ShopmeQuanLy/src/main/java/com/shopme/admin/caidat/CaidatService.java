package com.shopme.admin.caidat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TheloaiCaiDat;

@Service
public class CaidatService {

	@Autowired private CaidatReponsitory caiDatRepo;
	
	public List<CaiDat> listAllSettings() {
		return (List<CaiDat>) caiDatRepo.findAll();
	}
	public GeneralSettingBag getGeneralSettings() {
		List<CaiDat> settings = new ArrayList<>();
		
		List<CaiDat> generalSettings = caiDatRepo.findBytheLoai(TheloaiCaiDat.GENERAL);
		List<CaiDat> currencySettings = caiDatRepo.findBytheLoai(TheloaiCaiDat.CURRENCY);
		
		settings.addAll(generalSettings);
		settings.addAll(currencySettings);
		
		return new GeneralSettingBag(settings);
	}
	public void saveAll(Iterable<CaiDat> settings) {
		caiDatRepo.saveAll(settings);
	}
	
	public List<CaiDat> getMailServerSettings() {
		return caiDatRepo.findBytheLoai(TheloaiCaiDat.MAIL_SERVER);
	}
	
	public List<CaiDat> getMailTemplateSettings() {
		return caiDatRepo.findBytheLoai(TheloaiCaiDat.MAIL_TEMPLATES);
	}	
	public List<CaiDat> getCurrencySettings() {
		return caiDatRepo.findBytheLoai(TheloaiCaiDat.CURRENCY);
	}
	public List<CaiDat> getPaymentSettings() {
		return caiDatRepo.findBytheLoai(TheloaiCaiDat.PAYMENT);
	}	
}
