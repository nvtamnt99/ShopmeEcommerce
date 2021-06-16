package com.shopme.giohang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.KhachHang;
import com.shopme.common.entity.MatHangGioHang;
import com.shopme.common.entity.SanPham;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ShoppingCartTests {

	@Autowired private CartItemReponsitory cartRepo;
	
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testAddoneCartItem() {
		SanPham sanPham = entityManager.find(SanPham.class, 3);
		KhachHang khachHang = entityManager.find(KhachHang.class, 1);
		
		
		MatHangGioHang newItem = new MatHangGioHang();
		newItem.setKhachhang(khachHang);
		newItem.setSanpham(sanPham);
		newItem.setSoLuong(10);
		
		MatHangGioHang saveItem = cartRepo.save(newItem);
		
		assertThat(saveItem.getMaMHGH()).isGreaterThan(0);
	}
	
	@Test
	public void testGetCartItemsByCustomer() {
		KhachHang khachHang = new KhachHang();
		khachHang.setMaKhachHang(1);
		
		List<MatHangGioHang> cartItem = cartRepo.findByKhachhang(khachHang);
		
		assertEquals(1, cartItem.size());
	}
	
	
	
}
