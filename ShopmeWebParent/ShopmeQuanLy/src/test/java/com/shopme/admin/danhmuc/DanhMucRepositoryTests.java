package com.shopme.admin.danhmuc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.DanhMuc;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DanhMucRepositoryTests {
	
	@Autowired
	private DanhMucRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		DanhMuc category = new DanhMuc("thiết bị điện tử");
		DanhMuc savedCategory = repo.save(category);
		
		assertThat(savedCategory.getMaDanhMuc()).isGreaterThan(0);
	}
	
//	@Test 
//	public void testCreateSubCategory() {
//		DanhMuc parent = new DanhMuc(1);
//		DanhMuc subCategory = new DanhMuc("Máy tính bàn", parent);
//		DanhMuc savedCategory = repo.save(subCategory);
//		
//		assertThat(savedCategory.getMaDanhMuc()).isGreaterThan(0);
//	}
//	
	@Test 
	public void testCreateSubCategory() {
		DanhMuc parent = new DanhMuc(7);
		DanhMuc subCategory = new DanhMuc("iphone", parent);
		DanhMuc savedCategory = repo.save(subCategory);
		
		assertThat(savedCategory.getMaDanhMuc()).isGreaterThan(0);
	}
	
	@Test
	public void testGetCategory() {
		DanhMuc category = repo.findById(2).get();
		System.out.println(category.getTen());
		
		Set<DanhMuc> danhMucCon = category.getDanhMuccon();
		
		for (DanhMuc subCategory : danhMucCon) {
			System.out.println(subCategory.getTen());
		}
		
		assertThat(danhMucCon.size()).isGreaterThan(0);
	}
	
	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<DanhMuc> categories = repo.findAll();
		
		for (DanhMuc category : categories) {
			if (category.getDanhMucCha() == null) {
				System.out.println(category.getTen());
				
				Set<DanhMuc> danhMucCon = category.getDanhMuccon();
				
				for (DanhMuc subCategory : danhMucCon) {
					System.out.println("--" + subCategory.getTen());
					printChildren(subCategory, 1);
				}
			}
		}
	}
	
	private void printChildren(DanhMuc parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<DanhMuc> children = parent.getDanhMuccon();
		
		for (DanhMuc subCategory : children) {
			for (int i = 0; i < newSubLevel; i++) {
				System.out.print("--");
			}
			
			System.out.println(subCategory.getTen());
			
			printChildren(subCategory, newSubLevel);
		}
	}
	
	@Test
	public void testLietKeDanhMucGoc() {
		List<DanhMuc> lietKeDanhMucGoc = repo.findRootDanhMuc(Sort.by("ten").ascending());
		lietKeDanhMucGoc.forEach(cat -> System.out.println(cat.getTen()));
	}
	
	
	@Test
	public void testFindByTen() {
		String name = "máy tính";
		DanhMuc category = repo.findByTen(name);

		assertThat(category).isNotNull();
		assertThat(category.getTen()).isEqualTo(name);
	}


	@Test
	public void testFindByBiDanh() {
		String alias = "thiết bị điện tử";
		DanhMuc category = repo.findByBiDanh(alias);

		assertThat(category).isNotNull();
		assertThat(category.getBiDanh()).isEqualTo(alias);
	}
	
}
