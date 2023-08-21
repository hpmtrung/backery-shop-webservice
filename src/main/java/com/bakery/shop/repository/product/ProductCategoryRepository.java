package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>, ProductCategoryRepositoryCustom {}
