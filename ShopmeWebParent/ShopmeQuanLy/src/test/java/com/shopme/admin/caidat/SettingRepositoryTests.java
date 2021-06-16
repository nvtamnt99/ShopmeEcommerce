package com.shopme.admin.caidat;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TheloaiCaiDat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

	@Autowired CaidatReponsitory caidatReponsitory;
	
	@Test
	public void testCreateSetting() {
		CaiDat siteName = new CaiDat("SITE_NAME", "Shopme", TheloaiCaiDat.GENERAL);
		CaiDat siteLogo = new CaiDat("SITE_LOGO", "Shopme.png", TheloaiCaiDat.GENERAL);
		CaiDat copyright = new CaiDat("COPYRIGHT", "Copyright (C) 2021 Shopme Ltd.", TheloaiCaiDat.GENERAL);
		
		caidatReponsitory.saveAll(List.of(siteName, siteLogo, copyright));
		
		Iterable<CaiDat> iterable = caidatReponsitory.findAll();
		
		assertThat(iterable).size().isGreaterThan(0);
	}
	
	@Test
	public void testCreateCurrencySettings() {
		CaiDat currencyId = new CaiDat("CURRENCY_ID", "1", TheloaiCaiDat.CURRENCY);
		CaiDat symbol = new CaiDat("CURRENCY_SYMBOL", "$", TheloaiCaiDat.CURRENCY);
		CaiDat symbolPosition = new CaiDat("CURRENCY_SYMBOL_POSITION", "before", TheloaiCaiDat.CURRENCY);
		CaiDat decimalPointType = new CaiDat("DECIMAL_POINT_TYPE", "POINT", TheloaiCaiDat.CURRENCY);
		CaiDat decimalDigits = new CaiDat("DECIMAL_DIGITS", "2", TheloaiCaiDat.CURRENCY);
		CaiDat thousandsPointType = new CaiDat("THOUSANDS_POINT_TYPE", "COMMA", TheloaiCaiDat.CURRENCY);
		
		caidatReponsitory.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType, 
				decimalDigits, thousandsPointType));
		
	}
	@Test
	public void testListSettingsByCategory() {
		List<CaiDat> caidats = caidatReponsitory.findBytheLoai(TheloaiCaiDat.GENERAL);
		
		caidats.forEach(System.out::println);
	}
}
