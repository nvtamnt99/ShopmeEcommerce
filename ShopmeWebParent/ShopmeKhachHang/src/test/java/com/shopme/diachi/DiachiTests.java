package com.shopme.diachi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.DiaChi;
import com.shopme.common.entity.KhachHang;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DiachiTests {

	@Autowired private DiachiReponsitory diaChiRepo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testAddoneDiachi() {
		KhachHang khachHang = entityManager.find(KhachHang.class, 1);
		DatNuoc datNuoc = entityManager.find(DatNuoc.class, 2);
		
		DiaChi address = new DiaChi();
		address.setKhachHang(khachHang);
		address.setDatNuoc(datNuoc);
		address.setHo("Nguyễn");
		address.setTen("Nam");
		address.setSdt("646-232-3902");
		address.setDongDiachi1("Gò Vấp");
		address.setThanhPho("Hồ Chí Minh");
		address.setMaBuudien("+37");
		address.setChonMacdinh(true);
		
		DiaChi savedAddress = diaChiRepo.save(address);
		
		assertTrue(savedAddress.getMa() > 0);
		
	}
}
