package com.bakery.shop.service;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.service.dto.admin.product.AdminProductDTO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Product createProduct(AdminProductDTO adminProductDTO);

    void updateProductInfo(Integer productId, AdminProductDTO adminProductDTO);

    void addNewImagesToProduct(Integer productId, List<MultipartFile> images);

    void deleteImagesOfProduct(Integer productId, List<Integer> deletedImageIds);
}
