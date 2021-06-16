package com.shopme.admin.danhmuc;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.common.entity.DanhMuc;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class DanhMucServiceTests {
	@MockBean
	private DanhMucRepository repo;

	@InjectMocks
	private DanhMucService service;

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateName() {
		Integer maDanhMuc = null;
		String ten = "máy tính";
		String biDanh = "abc";

		DanhMuc category = new DanhMuc(maDanhMuc, ten, biDanh);

		Mockito.when(repo.findByTen(ten)).thenReturn(category);
		Mockito.when(repo.findByBiDanh(biDanh)).thenReturn(null);

		String result = service.checkUnique(maDanhMuc, ten, biDanh);

		assertThat(result).isEqualTo("DuplicateName");
	}

	@Test
	public void testCheckUniqueInNewModeReturnDuplicateAlias() {
		Integer maDanhMuc = null;
		String ten = "NameABC";
		String biDanh = "máy tính";

		DanhMuc category = new DanhMuc(maDanhMuc, ten, biDanh);

		Mockito.when(repo.findByTen(ten)).thenReturn(null);
		Mockito.when(repo.findByBiDanh(biDanh)).thenReturn(category);

		String result = service.checkUnique(maDanhMuc, ten, biDanh);

		assertThat(result).isEqualTo("DuplicateAlias");
	}	


	@Test
	public void testCheckUniqueInNewModeReturnOK() {
		Integer maDanhMuc = null;
		String ten = "NameABC";
		String biDanh = "máy tính";

		Mockito.when(repo.findByTen(ten)).thenReturn(null);
		Mockito.when(repo.findByBiDanh(biDanh)).thenReturn(null);

		String result = service.checkUnique(maDanhMuc, ten, biDanh);

		assertThat(result).isEqualTo("OK");
	}		

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateName() {
		Integer maDanhMuc = 1;
		String ten = "NameABC";
		String biDanh = "máy tính";

		DanhMuc category = new DanhMuc(2, ten, biDanh);

		Mockito.when(repo.findByTen(ten)).thenReturn(category);
		Mockito.when(repo.findByBiDanh(biDanh)).thenReturn(null);

		String result = service.checkUnique(maDanhMuc, ten, biDanh);

		assertThat(result).isEqualTo("DuplicateName");
	}

	@Test
	public void testCheckUniqueInEditModeReturnDuplicateAlias() {
		Integer maDanhMuc = 1;
		String ten = "NameABC";
		String biDanh = "máy tính";

		DanhMuc category = new DanhMuc(2, ten, biDanh);

		Mockito.when(repo.findByTen(ten)).thenReturn(null);
		Mockito.when(repo.findByBiDanh(biDanh)).thenReturn(category);

		String result = service.checkUnique(maDanhMuc, ten, biDanh);

		assertThat(result).isEqualTo("DuplicateAlias");
	}

	@Test
	public void testCheckUniqueInEditModeReturnOK() {
		Integer maDanhMuc = 1;
		String ten = "NameABC";
		String biDanh = "máy tính";

		DanhMuc category = new DanhMuc(maDanhMuc, ten, biDanh);

		Mockito.when(repo.findByTen(ten)).thenReturn(null);
		Mockito.when(repo.findByBiDanh(biDanh)).thenReturn(category);

		String result = service.checkUnique(maDanhMuc, ten, biDanh);

		assertThat(result).isEqualTo("OK");
	}
	
}
