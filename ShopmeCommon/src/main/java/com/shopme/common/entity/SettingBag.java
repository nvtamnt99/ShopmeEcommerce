package com.shopme.common.entity;

import java.util.List;

public class SettingBag {

	private List<CaiDat> listCaidat;

	public SettingBag(List<CaiDat> listCaidat) {
		this.listCaidat = listCaidat;
	}
	
	public CaiDat get(String key) {
		int index = listCaidat.indexOf(new CaiDat(key));
		if (index >= 0) {
			return listCaidat.get(index);	
		}		
		
		return null;
	}
	
	public String getValue(String key) {
		CaiDat setting = get(key);
		if (setting != null) {
			return setting.getGiaTri();
		}
		return null;
	}
	
	public void update(String key, String value) {
		CaiDat setting = get(key);
		if (setting != null) {
			setting.setGiaTri(value);;
		}
	}
	
	public List<CaiDat> list() {
		return listCaidat;
	}
	
	public void updateCurrencySymbol(String value) {
		update("CURRENCY_SYMBOL", value);
	}
	
	public void updateSiteLogo(String value) {
		update("SITE_LOGO", value);
	}
}
