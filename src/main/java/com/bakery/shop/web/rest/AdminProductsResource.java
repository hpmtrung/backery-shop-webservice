package com.bakery.shop.web.rest;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductImage;
import com.bakery.shop.repository.product.ProductCategoryRepository;
import com.bakery.shop.repository.product.ProductImageRepository;
import com.bakery.shop.repository.product.ProductRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.ProductService;
import com.bakery.shop.service.dto.admin.product.AdminProductDTO;
import com.bakery.shop.service.dto.admin.product.AdminVariantDTO;
import com.bakery.shop.web.rest.errors.*;
import com.bakery.shop.web.rest.vm.AdminOverviewProductVM;
import com.bakery.shop.web.rest.vm.DeleteProductImagesVM;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/admin/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminProductsResource {

    private final Logger log = LogManager.getLogger(AdminProductsResource.class);

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductService productService;

    public AdminProductsResource(
        ProductCategoryRepository productCategoryRepository,
        ProductRepository productRepository,
        ProductVariantRepository variantRepository,
        ProductImageRepository productImageRepository,
        ProductService productService
    ) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
        this.productImageRepository = productImageRepository;
        this.productService = productService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<AdminOverviewProductVM>> getProducts(@PageableDefault Pageable pageable) {
        Page<AdminOverviewProductVM> page = productRepository
            .findAll(pageable)
            .map(product ->
                new AdminOverviewProductVM()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setCategoryName(product.getCategory().getName())
                    .setIngredients(product.getIngredients())
            );
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @Transactional(readOnly = true)
    public AdminProductDTO getProductDetail(@PathVariable("productId") Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductIdNotFoundException::new);

        return new AdminProductDTO()
            .setId(product.getId())
            .setName(product.getName())
            .setCategoryId(product.getCategory().getId())
            .setIngredients(product.getIngredients())
            .setAllergens(product.getAllergens())
            .setAvailable(product.isAvailable());
    }

    @GetMapping("/variants/{productId}")
    @Transactional(readOnly = true)
    public List<AdminVariantDTO> getVariantsOfProduct(@PathVariable("productId") Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductIdNotFoundException::new);

        return variantRepository
            .findByProduct(product)
            .stream()
            .map(variant ->
                new AdminVariantDTO()
                    .setId(variant.getId())
                    .setProductId(productId)
                    .setTypeId(variant.getProductType().getId())
                    .setCost(variant.getCost())
                    .setPrice(variant.getPrice())
                    .setHot(variant.isHot())
                    .setAvailable(variant.isAvailable())
            )
            .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@Valid @RequestBody AdminProductDTO adminProductDTO) {
        refineAdminProductDTOProperties(adminProductDTO);

        if (productRepository.findByName(adminProductDTO.getName()).isPresent()) {
            throw new ProductNameAlreadyUsedException();
        }

        if (productCategoryRepository.findById(adminProductDTO.getCategoryId()).isEmpty()) {
            throw new CategoryIdNotFoundException();
        }

        Product createdProduct = productService.createProduct(adminProductDTO);
        return ResponseEntity.ok(createdProduct.getId());
    }

    @PutMapping("info")
    public void saveProductInfo(@Valid @RequestBody AdminProductDTO adminProductDTO) {
        if (adminProductDTO.getId() == null) {
            throw new ErrorEntityValidationException("Update product should have an id", "product", "nullId");
        }
        refineAdminProductDTOProperties(adminProductDTO);

        Product updatedProduct = productRepository.findById(adminProductDTO.getId()).orElseThrow(ProductIdNotFoundException::new);

        // Check name is valid
        if (
            !updatedProduct.getName().equals(adminProductDTO.getName()) &&
            productRepository.findByName(adminProductDTO.getName()).isPresent()
        ) {
            throw new ProductNameAlreadyUsedException();
        }

        // Check category id is valid
        if (
            !updatedProduct.getCategory().getId().equals(adminProductDTO.getCategoryId()) &&
            productCategoryRepository.findById(adminProductDTO.getCategoryId()).isEmpty()
        ) {
            throw new CategoryIdNotFoundException();
        }

        productService.updateProductInfo(adminProductDTO.getId(), adminProductDTO);
    }

    @GetMapping("/{productId}/images")
    @Transactional(readOnly = true)
    public List<ProductImage> getProductImages(@PathVariable Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductIdNotFoundException::new);
        final String imagePrefix = ResourceUtil.getProductImageURLPrefix();
        return product
            .getImages()
            .stream()
            .map(image -> image.setImagePath(imagePrefix + image.getImagePath()))
            .collect(Collectors.toList());
    }

    @PostMapping(value = "{productId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductImages(@PathVariable("productId") Integer productId, @RequestParam("images") final List<MultipartFile> images) {
        if (productRepository.findById(productId).isEmpty()) {
            throw new ProductIdNotFoundException();
        }

        if (images.isEmpty() || images.stream().anyMatch(MultipartFile::isEmpty)) {
            throw new BadRequestException("New images should not be empty", "uploadImageEmpty");
        }

        productService.addNewImagesToProduct(productId, images);
    }

    @DeleteMapping(value = "{productId}/images")
    public void deleteProductImages(
        @PathVariable("productId") Integer productId,
        @Valid @RequestBody DeleteProductImagesVM deleteProductImagesVM
    ) {
        final List<Integer> deletedImageIds = deleteProductImagesVM.getDeletedImageIds();

        if (productRepository.findById(productId).isEmpty()) {
            throw new ProductIdNotFoundException();
        }

        deletedImageIds.forEach(deletedImageId -> {
            ProductImage deletedImage = productImageRepository
                .findById(deletedImageId)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        String.format("Deleted image id '%d' is not found", deletedImageId),
                        "product",
                        "notFoundImageId"
                    )
                );
            if (!deletedImage.getProduct().getId().equals(productId)) {
                throw new ErrorEntityValidationException(
                    String.format("Deleted image id '%d' is not owned by product", deletedImageId),
                    "product",
                    "invalidImageProduct"
                );
            }
        });

        productService.deleteImagesOfProduct(productId, deletedImageIds);
    }

    private void refineAdminProductDTOProperties(AdminProductDTO adminProductDTO) {
        adminProductDTO
            .setName(adminProductDTO.getName().trim())
            .setAllergens(adminProductDTO.getAllergens() != null ? adminProductDTO.getAllergens().trim() : null)
            .setIngredients(adminProductDTO.getIngredients().trim());
    }
}
