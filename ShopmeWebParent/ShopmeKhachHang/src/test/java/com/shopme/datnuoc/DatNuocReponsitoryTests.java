package com.shopme.datnuoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.DatNuoc;
import com.shopme.khachhang.DatNuocReponsitory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DatNuocReponsitoryTests {

	@Autowired private DatNuocReponsitory datNuocReponsitory;
	
	@Test
	public void testCreateDatNuoc() {
		DatNuoc datNuoc_1 = new DatNuoc("Việt Nam", "+84");
		DatNuoc saveDatnuoc = datNuocReponsitory.save(datNuoc_1);
		assertThat(saveDatnuoc.getMaDatnuoc()).isGreaterThan(0);
	}
	
	@Test
	public void testfindAllDatNuoc() {
		Iterable<DatNuoc> listDatnuoc = (List<DatNuoc>) datNuocReponsitory.findAll();
		listDatnuoc.forEach(dn -> {
			System.out.println(dn);
		});
//		assertThat(dat)
	}
	
}
