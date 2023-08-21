package com.bakery.shop.service;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductImage;
import com.bakery.shop.repository.product.ProductCategoryRepository;
import com.bakery.shop.repository.product.ProductImageRepository;
import com.bakery.shop.repository.product.ProductRepository;
import com.bakery.shop.service.dto.admin.product.AdminProductDTO;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final FileService fileService;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductCategoryRepository categoryRepository;

    public ProductServiceImpl(
        FileService fileService,
        ProductRepository productRepository,
        ProductImageRepository productImageRepository,
        ProductCategoryRepository categoryRepository
    ) {
        this.fileService = fileService;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public Product createProduct(AdminProductDTO adminProductDTO) {
        Product newProduct = new Product();
        newProduct.setName(adminProductDTO.getName());
        newProduct.setIngredients(adminProductDTO.getIngredients());
        newProduct.setAllergens(adminProductDTO.getAllergens());
        newProduct.setAvailable(true);
        categoryRepository.findById(adminProductDTO.getCategoryId()).orElseThrow().addProduct(newProduct);
        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void updateProductInfo(Integer productId, AdminProductDTO adminProductDTO) {
        productRepository
            .findById(productId)
            .ifPresent(product -> {
                product.setName(adminProductDTO.getName());
                product.setIngredients(adminProductDTO.getIngredients());
                product.setAllergens(adminProductDTO.getAllergens());
                product.setAvailable(adminProductDTO.isAvailable());
                if (!product.getCategory().getId().equals(adminProductDTO.getCategoryId())) {
                    product.getCategory().removeProduct(product);
                    categoryRepository.findById(adminProductDTO.getCategoryId()).orElseThrow().addProduct(product);
                }
                productRepository.save(product);
            });
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void addNewImagesToProduct(Integer productId, List<MultipartFile> images) {
        Product product = productRepository.findById(productId).orElseThrow();
        images.forEach(image -> {
            ProductImage newProductImage = new ProductImage();
            newProductImage.setImagePath(generateProductImageFileName(Objects.requireNonNull(image.getOriginalFilename())));
            fileService.saveProductImage(image, newProductImage.getImagePath());
            product.addProductImage(newProductImage);
        });
        productRepository.save(product);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void deleteImagesOfProduct(Integer productId, List<Integer> deletedImageIds) {
        Product product = productRepository.findById(productId).orElseThrow();
        deletedImageIds.forEach(deletedImageId -> {
            ProductImage deletedImage = productImageRepository.findById(deletedImageId).orElseThrow();
            fileService.deleteProductImage(deletedImage);
            product.removeProductImage(deletedImage);
            // Why: đã có orphanRemoval=true
            productImageRepository.delete(deletedImage);
            productImageRepository.flush();
        });
        productRepository.save(product);
    }

    private String generateProductImageFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        Long epoch = ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now());
        return epoch + extension;
    }
}
