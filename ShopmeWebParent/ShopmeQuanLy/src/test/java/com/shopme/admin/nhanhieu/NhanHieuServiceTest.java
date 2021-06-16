package com.shopme.admin.nhanhieu;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.common.entity.NhanHieu;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class NhanHieuServiceTest {
	@MockBean private NhanHieuRepository repo;

	@InjectMocks private NhanHieuService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicate() {
		Integer maNhanHieu = null;
		String ten = "Acer";
		NhanHieu brand = new NhanHieu(ten);

		Mockito.when(repo.findByTen(ten)).thenReturn(brand);

		String result = service.checkUnique(maNhanHieu, ten);
		assertThat(result).isEqualTo("Duplicate");
	}

	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer maNhanHieu = null;
		String ten = "AMD";

		Mockito.when(repo.findByTen(ten)).thenReturn(null);

		String result = service.checkUnique(maNhanHieu, ten);
		assertThat(result).isEqualTo("OK");
	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicate() {
		Integer maNhanHieu = 1;
		String ten = "Canon";
		NhanHieu brand = new NhanHieu(maNhanHieu, ten);

		Mockito.when(repo.findByTen(ten)).thenReturn(brand);

		String result = service.checkUnique(2, "Canon");
		assertThat(result).isEqualTo("Duplicate");
	}

	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer maNhanHieu = 1;
		String ten = "Acer";
		NhanHieu brand = new NhanHieu(maNhanHieu, ten);

		Mockito.when(repo.findByTen(ten)).thenReturn(brand);

		String result = service.checkUnique(maNhanHieu, "Acer Ltd");
		assertThat(result).isEqualTo("OK");
	}
}
