package com.shopme.admin.danhmuc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DanhMuc;
import com.shopme.common.exception.DanhMucNotFoundException;

@Service
@Transactional
public class DanhMucService {
	public static final int ROOT_CATEGORIES_PER_PAGE = 1;
	
	@Autowired
	private DanhMucRepository repo;
	
	public List<DanhMuc> listByPage(DanhMucPageInfo pageInfo, int pageNum, String sortDir,
			 String keyword) {
		Sort sort = Sort.by("ten");

		if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}

		Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

		Page<DanhMuc> pageCategories = null;
		
		if (keyword != null && !keyword.isEmpty()) {
			pageCategories = repo.search(keyword, pageable);	
		} else {
			pageCategories = repo.findRootDanhMuc(pageable);
		}
		
		List<DanhMuc> rootCategories = pageCategories.getContent();

		pageInfo.setTotalElements(pageCategories.getTotalElements());
		pageInfo.setTotalPages(pageCategories.getTotalPages());

		if (keyword != null && !keyword.isEmpty()) {
			List<DanhMuc> searchResult = pageCategories.getContent();
			for (DanhMuc category : searchResult) {
				category.setHasDanhMucCon(category.getDanhMuccon().size() > 0);
			}

			return searchResult;

		} else {
			return listHierarchicalCategories(rootCategories, sortDir);
		}
	}
	
	private List<DanhMuc> listHierarchicalCategories(List<DanhMuc> rootCategories, String sortDir) {
		List<DanhMuc> hierarchicalCategories = new ArrayList<>();
		
		for (DanhMuc rootCategory : rootCategories) {
			hierarchicalCategories.add(DanhMuc.copyTatCa(rootCategory));
			
			Set<DanhMuc> children = sortSubCategories(rootCategory.getDanhMuccon(), sortDir);
			
			for (DanhMuc subCategory : children) {
				String name = "--" + subCategory.getTen();
				hierarchicalCategories.add(DanhMuc.copyTatCa(subCategory, name));
				
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
			}
		}
		
		return hierarchicalCategories;
	}
	
	private void listSubHierarchicalCategories(List<DanhMuc> hierarchicalCategories, 
			DanhMuc parent, int subLevel, String sortDir) {
		Set<DanhMuc> children = sortSubCategories(parent.getDanhMuccon(), sortDir);
		int newSubLevel = subLevel + 1;
		
		for (DanhMuc subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getTen();
			
			hierarchicalCategories.add(DanhMuc.copyTatCa(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
		}
		
	}
	
	public DanhMuc save(DanhMuc category) {
		DanhMuc parent = category.getDanhMucCha();
		if (parent != null) {
			String allParentIds = parent.getTatCaMaDanhMucCha() == null ? "-" : parent.getTatCaMaDanhMucCha();
			allParentIds += String.valueOf(parent.getMaDanhMuc()) + "-";
			category.setTatCaMaDanhMucCha(allParentIds);
		}
		
		return repo.save(category);
	}

	public List<DanhMuc> listCategoriesUsedInForm() {
		List<DanhMuc> categoriesUsedInForm = new ArrayList<>();

		Iterable<DanhMuc> categoriesInDB = repo.findRootDanhMuc(Sort.by("ten").ascending());

		for (DanhMuc category : categoriesInDB) {
			categoriesUsedInForm.add(DanhMuc.copyMaVaTen(category));

			Set<DanhMuc> children = sortSubCategories(category.getDanhMuccon());

			for (DanhMuc subCategory : children) {
				String name = "--" + subCategory.getTen();
				categoriesUsedInForm.add(DanhMuc.copyMaVaTen(subCategory.getMaDanhMuc(), name));

				listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
			}
		}		

		return categoriesUsedInForm;
	}

	private void listSubCategoriesUsedInForm(List<DanhMuc> categoriesUsedInForm, 
			DanhMuc parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<DanhMuc> children = sortSubCategories(parent.getDanhMuccon());

		for (DanhMuc subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getTen();

			categoriesUsedInForm.add(DanhMuc.copyMaVaTen(subCategory.getMaDanhMuc(), name));

			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}		
	}

	public DanhMuc get(Integer maDanhMuc) throws DanhMucNotFoundException {
		try {
			return repo.findById(maDanhMuc).get();
		} catch (NoSuchElementException ex) {
			throw new DanhMucNotFoundException("Could not find any category with ID " + maDanhMuc);
		}
	}
	
	public String checkUnique(Integer maDanhMuc, String ten, String biDanh) {
		boolean isCreatingNew = (maDanhMuc == null || maDanhMuc == 0);

		DanhMuc categoryByName = repo.findByTen(ten);

		if (isCreatingNew) {
			if (categoryByName != null) {
				return "DuplicateName";
			} else {
				DanhMuc categoryByAlias = repo.findByBiDanh(biDanh);
				if (categoryByAlias != null) {
					return "DuplicateAlias";	
				}
			}
		} else {
			if (categoryByName != null && categoryByName.getMaDanhMuc() != maDanhMuc) {
				return "DuplicateName";
			}

			DanhMuc categoryByAlias = repo.findByBiDanh(biDanh);
			if (categoryByAlias != null && categoryByAlias.getMaDanhMuc() != maDanhMuc) {
				return "DuplicateAlias";
			}

		}

		return "OK";
	}
	
	private SortedSet<DanhMuc> sortSubCategories(Set<DanhMuc> children) {
		return sortSubCategories(children, "asc");
	}

	private SortedSet<DanhMuc> sortSubCategories(Set<DanhMuc> children, String sortDir) {
		SortedSet<DanhMuc> sortedChildren = new TreeSet<>(new Comparator<DanhMuc>() {
			@Override
			public int compare(DanhMuc cat1, DanhMuc cat2) {
				if (sortDir.equals("asc")) {
					return cat1.getTen().compareTo(cat2.getTen());
				} else {
					return cat2.getTen().compareTo(cat1.getTen());
				}
			}
		});

		sortedChildren.addAll(children);

		return sortedChildren;
	}
	

	public void updateCategoryEnabledStatus(Integer maDanhMuc, boolean trangThai) {
		repo.updateEnabledStatus(maDanhMuc, trangThai);
	}
	
	public void delete(Integer maDanhMuc) throws DanhMucNotFoundException {
		Long countByMaDanhMuc = repo.countBymaDanhMuc(maDanhMuc);
		if (countByMaDanhMuc == null || countByMaDanhMuc == 0) {
			throw new DanhMucNotFoundException("Không thể tìm thấy bất kỳ danh mục nào có ID " + maDanhMuc);
		}

		repo.deleteById(maDanhMuc);
	}
}
