package com.shopme.tinh;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.Tinh;
import com.shopme.khachhang.TinhReponsitory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TinhReponsitoryTest {

	@Autowired private TinhReponsitory tinhReponsitory;
	
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testCreateTinh() {
		DatNuoc datNuocAdmin = entityManager.find(DatNuoc.class, 1);
		Tinh tinh = new Tinh("Nghe An", datNuocAdmin);
		
		Tinh saveTinh = tinhReponsitory.save(tinh);
		
		assertThat(saveTinh.getMaTinh()).isGreaterThan(0);
	}
}
