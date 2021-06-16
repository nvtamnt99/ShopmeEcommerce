package com.shopme.khachhang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.KhachHang;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class KhachHangReponsitoryTest {

	@Autowired private KhachHangReponsitory khachhangReponsitory;
	@Autowired private TestEntityManager entityManager;

	@Test
	public void testCreateKhachhang() {
		DatNuoc datNuoc = entityManager.find(DatNuoc.class, 1);
		KhachHang khachHangDemo = new KhachHang("nguyentruongnam@gmail.com", "nam2020", 
				"Nguyen Truong Nam", "0357246123", "Go vap", "Nghe An", "HCM", "Nghe An", "411", true);
		khachHangDemo.setDatNuoc(datNuoc);
		KhachHang saveKhachhang = khachhangReponsitory.save(khachHangDemo);
		assertThat(saveKhachhang.getMaKhachHang()).isGreaterThan(0);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "nguyentruongnam69@gmail.com";
		KhachHang khachHang = khachhangReponsitory.getKhachhangByEmail(email);
		assertThat(khachHang).isNotNull();
	}
	
	@Test
	public void testGetAllKhachHang() {
		Iterable<KhachHang> listKhachhang = (List<KhachHang>) khachhangReponsitory.findAll();
		listKhachhang.forEach(kh -> System.out.println(kh));
	}
	@Test
	public void testUpdateAuthenticationType() {
		int id =1;
		khachhangReponsitory.updateAuthenticationType(id, AuthenticationType.GOOGLE	);
		KhachHang khachHang = khachhangReponsitory.findById(id).get();
		assertThat(khachHang.getAuthenticationType()).isEqualTo(AuthenticationType.GOOGLE);
	}
}
