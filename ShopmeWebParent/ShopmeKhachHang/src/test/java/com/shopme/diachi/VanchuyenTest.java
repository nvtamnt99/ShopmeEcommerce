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
import com.shopme.common.entity.GiaVanChuyen;
import com.shopme.vanchuyen.VanchuyenReponsitory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class VanchuyenTest {

	@Autowired private VanchuyenReponsitory vanChuyenRepo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testaddOneVanChuyen() {
		DatNuoc datNuoc = entityManager.find(DatNuoc.class, 2);
		GiaVanChuyen giaVanChuyen = new GiaVanChuyen();
		giaVanChuyen.setDatNuoc(datNuoc);
		giaVanChuyen.setGia(30000.0f);
		giaVanChuyen.setHoTroCOD(true);
		giaVanChuyen.setSoNgay(3);
		giaVanChuyen.setTinh("Hồ Chí Minh");
		
		GiaVanChuyen savedVanChuyen = vanChuyenRepo.save(giaVanChuyen);
		
		assertTrue(savedVanChuyen.getMa() > 0);
	}
	
	@Test
	public void testfindDatnuocandTinh() {
		DatNuoc datNuoc = entityManager.find(DatNuoc.class, 2);
		String tinh = "Nghệ An";
		GiaVanChuyen vanChuyen = vanChuyenRepo.findByDatNuocAndTinh(datNuoc, tinh);
		System.out.println(vanChuyen);
	}
}
