package com.shopme.admin.sanpham;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.SanPham;
import com.shopme.common.entity.HinhAnhSanPham;
public class SanPhamSaveHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(SanPhamSaveHelper.class);

	static void deleteExtraImagesWeredRemovedOnForm(SanPham product) {
		String extraImageDir = "../product-images/" + product.getMaSanPham() + "/extras";
		Path dirPath = Paths.get(extraImageDir);

		try {
			Files.list(dirPath).forEach(file -> {
				String filename = file.toFile().getName();

				if (!product.containsImageName(filename)) {
					try {
						Files.delete(file);
						LOGGER.info("Đã xóa hình ảnh: " + filename);

					} catch (IOException e) {
						LOGGER.error("Không thể xóa hình ảnh: " + filename);
					}
				}

			});
		} catch (IOException ex) {
			LOGGER.error("Không thể liệt kê thư mục: " + dirPath);
		}
	}

	static void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, 
			SanPham product) {
		if (imageIDs == null || imageIDs.length == 0) return;

		Set<HinhAnhSanPham> images = new HashSet<>();

		for (int count = 0; count < imageIDs.length; count++) {
			Integer maHinhAnh = Integer.parseInt(imageIDs[count]);
			String ten = imageNames[count];

			images.add(new HinhAnhSanPham(maHinhAnh, ten, product));
		}

		product.setHinhAnh(images);

	}

	static void setProductDetails(String[] detailIDs, String[] detailNames, 
			String[] detailValues, SanPham product) {
		if (detailNames == null || detailNames.length == 0) return;

		for (int count = 0; count < detailNames.length; count++) {
			String ten = detailNames[count];
			String value = detailValues[count];
			Integer maChiTietSP = Integer.parseInt(detailIDs[count]);

			if (maChiTietSP != 0) {
				product.themChiTiet(maChiTietSP, ten, value);
			} else if (!ten.isEmpty() && !value.isEmpty()) {
				product.themChiTietSP(ten, value);
			}
		}
	}

	static void saveUploadedImages(MultipartFile mainImageMultipart, 
			MultipartFile[] extraImageMultiparts, SanPham savedProduct) throws IOException {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			String uploadDir = "../product-images/" + savedProduct.getMaSanPham();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);		
		}

		if (extraImageMultiparts.length > 0) {
			String uploadDir = "../product-images/" + savedProduct.getMaSanPham() + "/extras";

			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (multipartFile.isEmpty()) continue;

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}
		}

	}

	static void setNewExtraImageNames(MultipartFile[] extraImageMultiparts, SanPham product) {
		if (extraImageMultiparts.length > 0) {
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

					if (!product.containsImageName(fileName)) {
						product.themHinhAnh(fileName);
					}
				}
			}
		}
	}

	static void setMainImageName(MultipartFile mainImageMultipart, SanPham product) {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			product.setHinhAnhChinh(fileName);
		}
	}
}
