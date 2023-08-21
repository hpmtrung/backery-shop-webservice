package com.bakery.shop.service;

import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.service.dto.admin.product.AdminVariantDTO;

public interface ProductVariantService {
    ProductVariant createVariant(AdminVariantDTO adminVariantDTO);
    void updateVariant(AdminVariantDTO adminVariantDTO);
}
