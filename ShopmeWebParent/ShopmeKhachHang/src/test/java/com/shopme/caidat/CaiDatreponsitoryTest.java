package com.shopme.caidat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.CaiDat;
import com.shopme.common.entity.TheloaiCaiDat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CaiDatreponsitoryTest {

	
	@Autowired private CaidatReponsitory rpo;
	
	@Test
	public void testFindByTwoTheloai() {
		List<CaiDat> ds = rpo.findByTwotheLoai(TheloaiCaiDat.GENERAL, TheloaiCaiDat.CURRENCY);
		ds.forEach(tt -> {
			System.out.println(tt);
		});
	}
}
