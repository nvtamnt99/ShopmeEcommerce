package com.shopme.danhmuc;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.shopme.common.entity.DanhMuc;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DanhMucRepositoryTests {
	@Autowired private DanhMucRepository repo;

	@Test
	public void testListEnabledCategories() {
		List<DanhMuc> categories = repo.findAllTrangThai();
		categories.forEach(category -> {
			System.out.println(category.getTen() + " (" + category.isTrangThai() + ")");
		});
	}
	
	@Test
	public void testFindCategoryByAlias() {
		String alias = "electronics";
		DanhMuc category = repo.findByAliasEnabled(alias);

		assertThat(category).isNotNull();
	}
}
