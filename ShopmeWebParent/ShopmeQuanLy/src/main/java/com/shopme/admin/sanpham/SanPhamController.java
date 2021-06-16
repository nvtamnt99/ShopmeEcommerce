package com.shopme.admin.sanpham;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.nhanhieu.NhanHieuService;
import com.shopme.admin.danhmuc.DanhMucService;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.DanhMuc;
import com.shopme.common.entity.SanPham;
import com.shopme.common.exception.SanPhamNotFoundException;

@Controller
public class SanPhamController {
	
	@Autowired private SanPhamService sanPhamService;
	@Autowired private NhanHieuService nhanHieuService;
	@Autowired private DanhMucService danhMucService;

	@GetMapping("/sanpham")
	public String listFirstPage(Model model) {
		return listByPage(1, model, "ten", "asc", null, 0);
	}
	
	@GetMapping("/sanpham/page/{pageNum}")
	public String listByPage(
			@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword,
			@Param("categoryId") Integer categoryId
			) {
		Page<SanPham> page = sanPhamService.listByPage(pageNum, sortField, sortDir, keyword, categoryId);
		List<SanPham> listProducts = page.getContent();
		
		List<DanhMuc> listCategories = danhMucService.listCategoriesUsedInForm();

		long startCount = (pageNum - 1) * SanPhamService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + SanPhamService.PRODUCTS_PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		if (categoryId != null) model.addAttribute("categoryId", categoryId); 
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);		
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("listCategories", listCategories);		
		
		return "sanpham/sanpham";		
	}
	
	@GetMapping("/sanpham/new")
	public String newProduct(Model model) {
		List<NhanHieu> listBrands = nhanHieuService.listAll();

		SanPham product = new SanPham();
		product.setTrangThai(true);
		product.setTrongKho(true);

		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Tạo sản phẩm mới");
		model.addAttribute("numberOfExistingExtraImages", 0);

		return "sanpham/sanpham_form";
	}

	@PostMapping("/sanpham/save")
	public String saveProduct(SanPham product, RedirectAttributes ra,
			@RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,			
			@RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues,
			@RequestParam(name = "imageIDs", required = false) String[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames,
			@AuthenticationPrincipal ShopmeUserDetails loggedUser
			)
					throws IOException {
		
		if (loggedUser.hasPhanQuyen("Nhân viên bán hàng")) {
			sanPhamService.saveProductPrice(product);
			ra.addFlashAttribute("message", "The product has been saved successfully.");			
			return "redirect:/sanpham";			
		}
			
		SanPhamSaveHelper.setMainImageName(mainImageMultipart, product);
		SanPhamSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
		SanPhamSaveHelper.setNewExtraImageNames(extraImageMultiparts, product);
		SanPhamSaveHelper.setProductDetails(detailIDs, detailNames, detailValues, product);

		SanPham savedProduct = sanPhamService.save(product);

		SanPhamSaveHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);
		
		SanPhamSaveHelper.deleteExtraImagesWeredRemovedOnForm(product);

		ra.addFlashAttribute("message", "Sản phẩm đã được lưu thành công.");

		return "redirect:/sanpham";

	}
	
	@GetMapping("/sanpham/{maSanPham}/trangThai/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("maSanPham") Integer maSanPham,
			@PathVariable("status") boolean trangThai, RedirectAttributes redirectAttributes) {
		sanPhamService.updateProductEnabledStatus(maSanPham, trangThai);
		String status = trangThai ? "kích hoạt" : "tắt kích hoạt";
		String message = "Sản phẩm có ID " + maSanPham + " đã được " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/sanpham";
	}
	
	@GetMapping("/sanpham/delete/{maSanPham}")
	public String deleteProduct(@PathVariable(name = "maSanPham") Integer maSanPham, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			sanPhamService.delete(maSanPham);
			String productExtraImagesDir = "../product-images/" + maSanPham + "/extras";
			String productImagesDir = "../product-images/" + maSanPham;

			FileUploadUtil.removeDir(productExtraImagesDir);
			FileUploadUtil.removeDir(productImagesDir);

			redirectAttributes.addFlashAttribute("message", 
					"Sản phẩm có ID " + maSanPham + " đã được xóa thành công");
		} catch (SanPhamNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/sanpham";
	}
	
	@GetMapping("/sanpham/edit/{maSanPham}")
	public String editProduct(@PathVariable("maSanPham") Integer maSanPham, Model model,
			RedirectAttributes ra) {
		try {
			SanPham product = sanPhamService.get(maSanPham);
			List<NhanHieu> listBrands = nhanHieuService.listAll();
			Integer numberOfExistingExtraImages = product.getHinhAnh().size();

			model.addAttribute("product", product);
			model.addAttribute("listBrands", listBrands);
			model.addAttribute("pageTitle", "Chỉnh sửa thông tin sản phẩm (Mã sản phẩm: " + maSanPham + ")");
			model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);


			return "sanpham/sanpham_form";

		} catch (SanPhamNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/sanpham";
		}
	}
	
	@GetMapping("/sanpham/detail/{maSanPham}")
	public String viewProductDetails(@PathVariable("maSanPham") Integer maSanPham, Model model,
			RedirectAttributes ra) {
		try {
			SanPham product = sanPhamService.get(maSanPham);			
			model.addAttribute("product", product);		

			return "sanpham/sanpham_detail_modal";

		} catch (SanPhamNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/sanpham";
		}
	}
}
