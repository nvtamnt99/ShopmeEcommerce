package com.shopme.admin.vanchuyen;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.caidat.quocgia.QuocGiaReponsitory;
import com.shopme.common.entity.DatNuoc;
import com.shopme.common.entity.GiaVanChuyen;

@Service
@Transactional
public class GiaVanChuyenService {

public static final int RATES_PER_PAGE = 10;
	
	@Autowired
	private GiaVanChuyenReponsitory shipRepo;
	
	@Autowired
	private QuocGiaReponsitory countryRepo;
	public List<GiaVanChuyen> listAll() {
		return shipRepo.findAll();
	}
	
	public Page<GiaVanChuyen> listAll(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum - 1, RATES_PER_PAGE, sort);
		
		if (keyword != null) {
			return shipRepo.findAll(keyword, pageable);
		}
		
		return shipRepo.findAll(pageable);
	}	
	
	public List<DatNuoc> getCountryList() {
		return countryRepo.findAllByOrderByTenAsc();
	}	

	public void save(GiaVanChuyen rate) throws GiaVanChuyenAlreadyExitsException {
		GiaVanChuyen existRate = shipRepo.findByDatNuocAndTinh(
				rate.getDatNuoc().getMaDatnuoc(), rate.getTinh());
		
		if (rate.getMa() == null && existRate != null || (rate.getMa() != null && existRate != null && !existRate.equals(rate))) {
			throw new GiaVanChuyenAlreadyExitsException("Đã có giá cho điểm đến "
						+ rate.getDatNuoc().getTen() + ", " + rate.getTinh()); 					
		}
		shipRepo.save(rate);
	}
	
	public GiaVanChuyen get(Integer id) throws GiaVanChuyenNotFoundException {
		try {
			return shipRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new GiaVanChuyenNotFoundException("Không thể tìm thấy phí vận chuyển với ID " + id);
		}
	}
	
	public void delete(Integer id) throws GiaVanChuyenNotFoundException {
		Long count = shipRepo.countByMa(id);
		if (count == null || count == 0) {
			throw new GiaVanChuyenNotFoundException("Không thể tìm thấy phí vận chuyển với ID " + id);
			
		}
		shipRepo.deleteById(id);
	}
	
	public void updateCodSupport(Integer id, boolean codSupported) throws GiaVanChuyenNotFoundException {
		try {
			shipRepo.findById(id).get();
			shipRepo.updatehoTroCOD(id, codSupported);
		} catch (NoSuchElementException ex) {
			throw new GiaVanChuyenNotFoundException("Không thể tìm thấy phí vận chuyển với ID " + id);
		}		
	}	
}
