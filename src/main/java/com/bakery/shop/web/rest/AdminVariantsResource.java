package com.bakery.shop.web.rest;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductType;
import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.repository.product.ProductRepository;
import com.bakery.shop.repository.product.ProductTypeRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.ProductVariantService;
import com.bakery.shop.service.dto.admin.product.AdminVariantDTO;
import com.bakery.shop.web.rest.errors.*;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin/variants", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVariantsResource {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductVariantService variantService;

    public AdminVariantsResource(
        ProductRepository productRepository,
        ProductTypeRepository productTypeRepository,
        ProductVariantRepository variantRepository,
        ProductVariantService variantService
    ) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.variantRepository = variantRepository;
        this.variantService = variantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createVariant(@Valid @RequestBody AdminVariantDTO adminVariantDTO) {
        if (adminVariantDTO.getCost() >= adminVariantDTO.getPrice()) {
            throw new VariantCostPriceInvalidException();
        }
        Product product = productRepository.findById(adminVariantDTO.getProductId()).orElseThrow(ProductIdNotFoundException::new);

        ProductType type = productTypeRepository.findById(adminVariantDTO.getTypeId()).orElseThrow(ProductTypeIdNotFoundException::new);

        if (variantRepository.findByProductAndProductType(product, type).isPresent()) {
            throw new VariantIdAlreadyUsedException();
        }

        final ProductVariant createdVariant = variantService.createVariant(adminVariantDTO);

        return ResponseEntity.ok(createdVariant.getId());
    }

    @PutMapping
    public void updateProductVariant(@Valid @RequestBody AdminVariantDTO adminVariantDTO) {
        if (adminVariantDTO.getId() == null) {
            throw new ErrorEntityValidationException("Variant need to be " + "updated should have id", "product", "nullIdVariant");
        }

        ProductVariant updatedVariant = variantRepository.findById(adminVariantDTO.getId()).orElseThrow(VariantIdNotFoundException::new);

        if (adminVariantDTO.getCost() >= adminVariantDTO.getPrice()) {
            throw new VariantCostPriceInvalidException();
        }

        Product variantProduct = productRepository.findById(adminVariantDTO.getProductId()).orElseThrow(ProductIdNotFoundException::new);

        ProductType variantType = productTypeRepository
            .findById(adminVariantDTO.getTypeId())
            .orElseThrow(ProductTypeIdNotFoundException::new);

        ProductVariant existingVariantWithSameProductAndType = variantRepository
            .findByProductAndProductType(variantProduct, variantType)
            .orElse(null);

        if (
            existingVariantWithSameProductAndType != null && !existingVariantWithSameProductAndType.getId().equals(updatedVariant.getId())
        ) {
            throw new VariantIdAlreadyUsedException();
        }

        variantService.updateVariant(adminVariantDTO);
    }

    @DeleteMapping("/{variantId}")
    public void deleteVariant(@PathVariable("variantId") Integer variantId) {
        ProductVariant variant = variantRepository.findById(variantId).orElseThrow(VariantIdNotFoundException::new);
        if (variantRepository.isSold(variantId)) {
            throw new DeleteSoldProductVariantException();
        }
        variantRepository.delete(variant);
        variantRepository.flush();
    }
}
