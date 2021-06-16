package com.shopme.admin.nhanhieu;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.NhanHieu;

@Service
public class NhanHieuService {
	public static final int BRANDS_PER_PAGE = 4;
	
	@Autowired
	private NhanHieuRepository repo;

	public List<NhanHieu> listAll() {
		return (List<NhanHieu>) repo.findAll();
	}
	
	public Page<NhanHieu> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

		Pageable pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);

		if (keyword != null) {
			return repo.findAll(keyword, pageable);
		}

		return repo.findAll(pageable);		
	}
	
	public NhanHieu save(NhanHieu brand) {
		return repo.save(brand);
	}

	public NhanHieu get(Integer maNhanHieu) throws NhanHieuNotFoundException {
		try {
			return repo.findById(maNhanHieu).get();
		} catch (NoSuchElementException ex) {
			throw new NhanHieuNotFoundException("Không thể tìm thấy bất kỳ nhãn hiệu nào có ID " + maNhanHieu);
		}
	}

	public void delete(Integer maNhanHieu) throws NhanHieuNotFoundException {
		Long countByMaNhanHieu = repo.countByMaNhanHieu(maNhanHieu);

		if (countByMaNhanHieu == null || countByMaNhanHieu == 0) {
			throw new NhanHieuNotFoundException("Không thể tìm thấy bất kỳ nhãn hiệu nào có ID " + maNhanHieu);			
		}

		repo.deleteById(maNhanHieu);
	}
	
	public String checkUnique(Integer maNhanHieu, String ten) {
		boolean isCreatingNew = (maNhanHieu == null || maNhanHieu == 0);
		NhanHieu brandByName = repo.findByTen(ten);

		if (isCreatingNew) {
			if (brandByName != null) return "Duplicate";
		} else {
			if (brandByName != null && brandByName.getMaNhanHieu() != maNhanHieu) {
				return "Duplicate";
			}
		}

		return "OK";
	}
}
