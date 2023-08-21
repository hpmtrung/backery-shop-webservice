package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.ProductCategory;

public interface ProductCategoryRepositoryCustom {
    int countNumberOfAvailableProducts(ProductCategory category);
}
