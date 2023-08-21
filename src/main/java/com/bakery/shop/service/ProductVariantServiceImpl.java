package com.bakery.shop.service;

import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.repository.product.ProductRepository;
import com.bakery.shop.repository.product.ProductTypeRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.dto.admin.product.AdminVariantDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductRepository productRepository;
    private final ProductTypeRepository typeRepository;
    private final ProductVariantRepository variantRepository;

    public ProductVariantServiceImpl(
        ProductRepository productRepository,
        ProductTypeRepository typeRepository,
        ProductVariantRepository variantRepository
    ) {
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    @Transactional
    public ProductVariant createVariant(AdminVariantDTO adminVariantDTO) {
        ProductVariant newVariant = new ProductVariant()
            .setProduct(productRepository.findById(adminVariantDTO.getProductId()).orElseThrow())
            .setProductType(typeRepository.findById(adminVariantDTO.getTypeId()).orElseThrow())
            .setAvailable(adminVariantDTO.isAvailable())
            .setCost(adminVariantDTO.getCost())
            .setPrice(adminVariantDTO.getPrice());
        variantRepository.save(newVariant);
        return newVariant;
    }

    @Override
    @Transactional
    public void updateVariant(AdminVariantDTO adminVariantDTO) {
        ProductVariant updatedVariant = variantRepository
            .findById(adminVariantDTO.getId())
            .orElseThrow()
            .setProduct(productRepository.findById(adminVariantDTO.getProductId()).orElseThrow())
            .setProductType(typeRepository.findById(adminVariantDTO.getTypeId()).orElseThrow())
            .setAvailable(adminVariantDTO.isAvailable())
            .setHot(adminVariantDTO.isHot())
            .setCost(adminVariantDTO.getCost())
            .setPrice(adminVariantDTO.getPrice());
        variantRepository.save(updatedVariant);
    }
}
