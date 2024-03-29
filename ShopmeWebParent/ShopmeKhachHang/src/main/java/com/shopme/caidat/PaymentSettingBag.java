package com.shopme.caidat;

import java.util.List;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.SettingBag;

public class PaymentSettingBag extends SettingBag{

	public PaymentSettingBag(List<CaiDat> listCaidat) {
		super(listCaidat);
	}


	public String getURL() {
		return super.getValue("PAYPAL_API_BASE_URL");
	}
	
	public String getClientID() {
		return super.getValue("PAYPAL_API_CLIENT_ID");
	}
	
	public String getClientSecret() {
		return super.getValue("PAYPAL_API_CLIENT_SECRET");
	}	
}
