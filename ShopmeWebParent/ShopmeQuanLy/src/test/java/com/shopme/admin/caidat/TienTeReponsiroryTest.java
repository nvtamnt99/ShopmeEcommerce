package com.shopme.admin.caidat;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.TienTe;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TienTeReponsiroryTest {

	@Autowired private CurrencyReponsitory repo;
	
	@Test
	public void testCreateCurrencies() {
		List<TienTe> listCurrencies = Arrays.asList(
			new TienTe("United States Dollar", "$", "USD"),
			new TienTe("British Pound", "£", "GPB"),
			new TienTe("Japanese Yen", "¥", "JPY"),
			new TienTe("Euro", "€", "EUR"),
			new TienTe("Russian Ruble", "₽", "RUB"),
			new TienTe("South Korean Won", "₩", "KRW"),
			new TienTe("Chinese Yuan", "¥", "CNY"),
			new TienTe("Brazilian Real", "R$", "BRL"),
			new TienTe("Australian Dollar", "$", "AUD"),
			new TienTe("Canadian Dollar", "$", "CAD"),
			new TienTe("Vietnamese đồng ", "₫", "VND"),
			new TienTe("Indian Rupee", "₹", "INR")
		);
		
		repo.saveAll(listCurrencies);
		
		Iterable<TienTe> iterable = (List<TienTe>)repo.findAll();
		
		assertThat(iterable).size().isEqualTo(12);
	}
	
	
	@Test
	public void testfindAllTiente() {
		List<TienTe> dsTiente = repo.findAllByOrderByTenAsc();
		dsTiente.forEach(tt -> {
			System.out.println(tt);
		});
	}
}
