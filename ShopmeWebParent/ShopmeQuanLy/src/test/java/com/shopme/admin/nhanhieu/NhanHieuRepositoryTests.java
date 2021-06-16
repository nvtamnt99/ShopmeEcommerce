package com.shopme.admin.nhanhieu;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.DanhMuc;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class NhanHieuRepositoryTests {
	@Autowired
	private NhanHieuRepository repo;

	@Test
	public void testCreateBrand1() {
		DanhMuc laptops = new DanhMuc(6);
		NhanHieu acer = new NhanHieu("Acer");
		acer.getDanhmuc().add(laptops);

		NhanHieu savedBrand = repo.save(acer);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getMaNhanHieu()).isGreaterThan(0);
	}

	@Test
	public void testCreateBrand2() {
		DanhMuc cellphones = new DanhMuc(4);
		DanhMuc tablets = new DanhMuc(7);

		NhanHieu apple = new NhanHieu("Apple");
		apple.getDanhmuc().add(cellphones);
		apple.getDanhmuc().add(tablets);

		NhanHieu savedBrand = repo.save(apple);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getMaNhanHieu()).isGreaterThan(0);
	}

	@Test
	public void testCreateBrand3() {
		NhanHieu samsung = new NhanHieu("Samsung");

		samsung.getDanhmuc().add(new DanhMuc(29));	// category memory
		samsung.getDanhmuc().add(new DanhMuc(24));	// category internal hard drive

		NhanHieu savedBrand = repo.save(samsung);

		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getMaNhanHieu()).isGreaterThan(0);
	}

	@Test
	public void testFindAll() {
		Iterable<NhanHieu> brands = repo.findAll();
		brands.forEach(System.out::println);

		assertThat(brands).isNotEmpty();
	}

	@Test
	public void testGetById() {
		NhanHieu brand = repo.findById(2).get();

		assertThat(brand.getTen()).isEqualTo("Acer");
	}

	@Test
	public void testUpdateName() {
		String newName = "Samsung Electronics";
		NhanHieu samsung = repo.findById(3).get();
		samsung.setTen(newName);

		NhanHieu savedBrand = repo.save(samsung);
		assertThat(savedBrand.getTen()).isEqualTo(newName);
	}

	@Test
	public void testDelete() {
		Integer id = 2;
		repo.deleteById(id);

		Optional<NhanHieu> result = repo.findById(id);

		assertThat(result.isEmpty());
	}
}
