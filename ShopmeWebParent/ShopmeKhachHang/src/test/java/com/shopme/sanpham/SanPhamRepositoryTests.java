package com.shopme.sanpham;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shopme.common.entity.SanPham;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SanPhamRepositoryTests {
	
	@Autowired SanPhamRepository repo;

	@Test
	public void testFindByAlias() {
		String alias = "canon-eos-m50";
		SanPham product = repo.findByBiDanh(alias);

		assertThat(product).isNotNull();
	}
}
