package com.shopme.admin.sanpham;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.DanhMuc;
import com.shopme.common.entity.SanPham;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SanPhamRepositoryTests {
	@Autowired
	private SanPhamRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
		NhanHieu brand = entityManager.find(NhanHieu.class, 37);
		DanhMuc category = entityManager.find(DanhMuc.class, 5);

		SanPham product = new SanPham();
		product.setTen("Acer Aspire Desktop");
		product.setBiDanh("acer_aspire_desktop");
		product.setMoTaNgan("Short description for Acer Aspire");
		product.setMoTaDayDu("Full description for Acer Aspire");

		product.setNhanhieu(brand);
		product.setDanhmuc(category);

		product.setGiaBan(678);
		product.setChiPhi(600);
		product.setTrangThai(true);
		product.setTrongKho(true);

		product.setThoiGianTao(new Date());
		product.setThoiGianCapNhat(new Date());

		SanPham savedProduct = repo.save(product);

		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getMaSanPham()).isGreaterThan(0);
	}

	@Test
	public void testListAllProducts() {
		Iterable<SanPham> iterableProducts = repo.findAll();

		iterableProducts.forEach(System.out::println);
	}

	@Test
	public void testGetProduct() {
		Integer maSanPham = 1;
		SanPham product = repo.findById(maSanPham).get();
		System.out.println(product);

		assertThat(product).isNotNull();
	}

	@Test
	public void testUpdateProduct() {
		Integer maSanPham = 1;
		SanPham product = repo.findById(maSanPham).get();
		product.setGiaBan(499);

		repo.save(product);

		SanPham updatedProduct = entityManager.find(SanPham.class, maSanPham);

		assertThat(updatedProduct.getGiaBan()).isEqualTo(499);
	}

	@Test
	public void testDeleteProduct() {
		Integer maSanPham = 1;
		repo.deleteById(maSanPham);

		Optional<SanPham> result = repo.findById(maSanPham);

		assertThat(!result.isPresent());
	}
	
	@Test
	public void testSaveProductWithImages() {
		Integer productId = 2;
		SanPham product = repo.findById(productId).get();

		product.setHinhAnhChinh("main image.jpg");
		product.themHinhAnh("extra image 1.png");
		product.themHinhAnh("extra_image_2.png");
		product.themHinhAnh("extra-image3.png");

		SanPham savedProduct = repo.save(product);

		assertThat(savedProduct.getHinhAnh().size()).isEqualTo(3);
	}
	
	@Test
	public void testSaveProductWithDetails() {
		Integer productId = 2;
		SanPham product = repo.findById(productId).get();
		
		product.themChiTietSP("Device Memory", "128 GB");
		product.themChiTietSP("CPU Model", "MediaTek");
		product.themChiTietSP("OS", "Android 10");

		SanPham savedProduct = repo.save(product);
		assertThat(savedProduct.getChitiet()).isNotEmpty();		
	}
}
