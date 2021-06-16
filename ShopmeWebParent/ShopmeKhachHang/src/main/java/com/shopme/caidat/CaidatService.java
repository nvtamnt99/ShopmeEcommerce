package com.shopme.caidat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TheloaiCaiDat;
import com.shopme.common.entity.TienTe;
import com.shopme.tiente.TienTeReponsitory;

@Service
public class CaidatService {

	@Autowired private CaidatReponsitory caiDatRepo;
	
	@Autowired private TienTeReponsitory tienTeReponsitory;
	
	public EmailSettingBag getEmailSettings() {
		List<CaiDat> caiDat = getSettings(TheloaiCaiDat.MAIL_SERVER);
		caiDat.addAll(getSettings(TheloaiCaiDat.MAIL_TEMPLATES));
		return new EmailSettingBag(caiDat);
	}
	private List<CaiDat> getSettings(TheloaiCaiDat theLoai) {
		return caiDatRepo.findBytheLoai(theLoai);
	}
	public CurrencySettingBag getCurrencySettingBag() {
		return new CurrencySettingBag(getSettings(TheloaiCaiDat.CURRENCY));
	}
	public List<CaiDat> getGeneralSettings() {
		return caiDatRepo.findByTwotheLoai(TheloaiCaiDat.GENERAL, TheloaiCaiDat.CURRENCY);
	}
	public List<CaiDat> getCurrencySettings() {
		return getSettings(TheloaiCaiDat.CURRENCY);
	}
	public PaymentSettingBag getPaymentSettings() {
		return new PaymentSettingBag(getSettings(TheloaiCaiDat.PAYMENT));
	}
	public String getCurrencyCode() {
		CaiDat setting = caiDatRepo.findByTuKhoa("CURRENCY_ID");
		Integer currencyId = Integer.parseInt(setting.getGiaTri());
		TienTe currency = tienTeReponsitory.findById(currencyId).get();
		
		return currency.getMa();
	}	
}
