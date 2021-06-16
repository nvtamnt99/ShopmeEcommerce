package com.shopme.admin.phantrang;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface PhanTrangRepository<T, ID> extends PagingAndSortingRepository<T, ID> {

	public Page<T> findAll(String keyword, Pageable pageable);
}
