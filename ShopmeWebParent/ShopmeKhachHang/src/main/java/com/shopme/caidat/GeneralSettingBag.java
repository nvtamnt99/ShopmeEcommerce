package com.shopme.caidat;

import java.util.List;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.SettingBag;

public class GeneralSettingBag extends SettingBag{

	public GeneralSettingBag(List<CaiDat> listCaidat) {
		super(listCaidat);
		// TODO Auto-generated constructor stub
	}
	public void updateCurrencySymbol(String value) {
		super.update("CURRENCY_SYMBOL", value);
	}
	
	public void updateSiteLogo(String value) {
		super.update("SITE_LOGO", value);
	}

}
