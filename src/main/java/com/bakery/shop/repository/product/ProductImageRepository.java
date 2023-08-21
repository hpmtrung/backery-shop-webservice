package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    List<ProductImage> findByProduct(Product product);
}
