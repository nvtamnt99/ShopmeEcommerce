package com.shopme.admin.nguoidung;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.PhanQuyen;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class PhanQuyenRepositoryTests {
	
	@Autowired
	private PhanQuyenRepository repo;
	
	@Test
	public void testCreateFirstRole(){
		PhanQuyen roleAdmin = new PhanQuyen("Quản trị viên", "có tất cả các quyền");
		PhanQuyen savedRole = repo.save(roleAdmin);
		
		assertThat(savedRole.getMaPhanQuyen()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		PhanQuyen roleSalesperson = new PhanQuyen("Nhân viên bán hàng", "Quản lý giá sản phẩm, "
				+ "Khách hàng, giao hàng, đặt hàng và báo cáo bán hàng");
		
		PhanQuyen roleEditor = new PhanQuyen("Người chỉnh sửa nội dung", "Quản lý danh mục, "
				+ "thương hiệu, bài báo cáo, sản phẩm, menu");
		
		PhanQuyen roleShipper = new PhanQuyen("Người giao hàng", "Xem sản phẩm, "
				+ "đơn đặt hàng, hóa đơn và cập nhật trạng thái của đơn hàng");
		
		PhanQuyen roleAssistant = new PhanQuyen("Phụ tá", "Quản lý câu hỏi "
				+ "và nhận xét, đánh giá của khách hàng");
		
		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
		
	}
}
