package com.shopme.admin.nguoidung;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.PhanQuyen;

@Repository
public interface PhanQuyenRepository extends CrudRepository<PhanQuyen, Integer>{
}
