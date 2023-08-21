package com.bakery.shop.web.rest;

import com.bakery.shop.domain.product.ProductCategory;
import com.bakery.shop.domain.product.ProductType;
import com.bakery.shop.repository.product.ProductCategoryRepository;
import com.bakery.shop.repository.product.ProductRepository;
import com.bakery.shop.repository.product.ProductTypeRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.CartService;
import com.bakery.shop.service.FileService;
import com.bakery.shop.service.dto.PublicCheckOutInitDTO;
import com.bakery.shop.service.dto.user.UserCheckOutFinishDTO;
import com.bakery.shop.web.rest.errors.CategoryIdNotFoundException;
import com.bakery.shop.web.rest.errors.VariantIdNotFoundException;
import com.bakery.shop.web.rest.vm.PublicCategoryVM;
import com.bakery.shop.web.rest.vm.PublicProductVM;
import com.bakery.shop.web.rest.vm.PublicVariantVM;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/public", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

    private final FileService fileService;
    private final ProductCategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ProductVariantRepository variantRepository;
    private final ProductTypeRepository productTypeRepository;

    public PublicResource(
        ProductCategoryRepository categoryRepository,
        ProductRepository productRepository,
        FileService fileService,
        CartService cartService,
        ProductVariantRepository variantRepository,
        ProductTypeRepository productTypeRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.fileService = fileService;
        this.cartService = cartService;
        this.variantRepository = variantRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @GetMapping("/categories")
    public List<PublicCategoryVM> getAllCategories() {
        final String url = ResourceUtil.getCategoryImageURLPrefix();

        return categoryRepository
            .findAll()
            .stream()
            .map(category ->
                new PublicCategoryVM()
                    .setId(category.getId())
                    .setName(category.getName())
                    .setImageUrl(url + category.getImageUrl())
                    .setBanner(url + category.getBanner())
                    .setIcon(url + category.getIcon())
            )
            .collect(Collectors.toList());
    }

    @GetMapping("/categories/{id}/products")
    @Transactional(readOnly = true)
    public ResponseEntity<List<PublicProductVM>> getProductsOfCategory(@PathVariable("id") Integer id, @PageableDefault Pageable pageable) {
        ProductCategory category = categoryRepository.findById(id).orElseThrow(CategoryIdNotFoundException::new);

        final String imageURLPrefix = ResourceUtil.getProductImageURLPrefix();

        Page<PublicProductVM> page = productRepository
            .findAllByCategoryAndAvailableIsTrue(category, pageable)
            .map(product ->
                new PublicProductVM()
                    .setId(product.getId())
                    .setName(product.getName())
                    .setAllergens(product.getAllergens())
                    .setIngredients(product.getIngredients())
                    .setImageUrls(
                        product.getImages().stream().map(image -> imageURLPrefix + image.getImagePath()).collect(Collectors.toList())
                    )
                    .setVariants(
                        product
                            .getVariants()
                            .stream()
                            .map(variant ->
                                new PublicVariantVM()
                                    .setId(variant.getId())
                                    .setTypeName(variant.getProductType().getName())
                                    .setUnitPrice(variant.getPrice())
                            )
                            .collect(Collectors.toList())
                    )
            );

        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/product-types")
    public List<ProductType> getProductTypes() {
        return productTypeRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/image/{entity}/{fileName:.+}")
    public ResponseEntity<Resource> serveImageEntityFile(@PathVariable String entity, @PathVariable String fileName) {
        Resource file = null;
        switch (entity) {
            case "category":
                file = fileService.loadCategoryImageAsResource(fileName);
                break;
            case "product":
                file = fileService.loadProductImageAsResource(fileName);
                break;
            case "user":
                file = fileService.loadUserImageAsResource(fileName);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
        // return ResponseEntity
        //     .ok()
        //     .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        //     .body(file);
    }

    @PostMapping("/checkout")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Throwable.class })
    public UserCheckOutFinishDTO checkOutUnAuthenticatedUser(@Valid @RequestBody PublicCheckOutInitDTO checkOutInitDTO) {
        checkOutInitDTO
            .getCartDetails()
            .forEach(cartDetailVM -> {
                if (variantRepository.findById(cartDetailVM.getVariantId()).isEmpty()) {
                    throw new VariantIdNotFoundException();
                }
            });
        return cartService.checkoutUnAuthenticatedUser(checkOutInitDTO);
    }
}
