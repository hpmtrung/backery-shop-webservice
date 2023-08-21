// package com.bakery.shop.web.rest.admin;
//
// import com.bakery.shop.IntegrationTest;
// import com.bakery.shop.domain.product.Product;
// import com.bakery.shop.domain.product.ProductCategory;
// import com.bakery.shop.domain.product.ProductImage;
// import com.bakery.shop.repository.product.ProductCategoryRepository;
// import com.bakery.shop.repository.product.ProductImageRepository;
// import com.bakery.shop.repository.product.ProductRepository;
// import com.bakery.shop.security.AuthoritiesConstants;
// import com.bakery.shop.service.FileService;
// import com.bakery.shop.web.rest.AdminProductsResource;
// import com.bakery.shop.web.rest.TestUtil;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.http.MediaType;
// import org.springframework.mock.web.MockMultipartFile;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.util.List;
// import java.util.function.Consumer;
// import java.util.stream.Collectors;
// import java.util.stream.StreamSupport;
//
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// /** Integration tests for the {@link AdminProductsResource} REST controller. */
// @AutoConfigureMockMvc
// @WithMockUser(authorities = AuthoritiesConstants.ADMIN)
// @IntegrationTest
// class AdminProductsResourceIT {
//
//   private static final int TEST_PRODUCT_ID_HAS_IMAGE = 39;
//   private static final int TEST_PRODUCT_ID_DOES_NOT_HAS_IMAGE = 48;
//   private static final String TEST_PRODUCT_NAME = "test-name";
//   private static final String TEST_PRODUCT_INGREDIENTS = "test-ingredients";
//   private static final String TEST_PRODUCT_ALLERGENS = "test-allergens";
//   private static final ClassPathResource TEST_PRODUCT_IMAGE_1 =
//       new ClassPathResource("test-product-img-1.jpg", AdminProductsResourceIT.class);
//   private static final ClassPathResource TEST_PRODUCT_IMAGE_2 =
//       new ClassPathResource("test-product-img-2.jpg", AdminProductsResourceIT.class);
//
//   @Autowired private ProductCategoryRepository categoryRepository;
//
//   @Autowired private ProductRepository productRepository;
//
//   @Autowired private ProductImageRepository productImageRepository;
//
//   @Autowired private FileService fileService;
//
//   @Autowired private MockMvc restMockMvc;
//
//   @Test
//   @Transactional
//   public void testGetAllProducts() throws Exception {
//     restMockMvc
//         .perform(get("/api/admin/products").accept(MediaType.APPLICATION_JSON))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//         .andExpect(jsonPath("$.[*].id").isNotEmpty())
//         .andExpect(jsonPath("$.[*].name").isNotEmpty())
//         .andExpect(jsonPath("$.[*].ingredients").isNotEmpty())
//         .andExpect(jsonPath("$.[*].allergens").exists())
//         .andExpect(jsonPath("$.[*].available").isNotEmpty())
//         //
//         .andExpect(jsonPath("$.[*].category").exists())
//         .andExpect(jsonPath("$.[*].category.id").isNotEmpty())
//         .andExpect(jsonPath("$.[*].category.name").isNotEmpty())
//         .andExpect(jsonPath("$.[*].category.image").doesNotExist())
//         .andExpect(jsonPath("$.[*].category.icon").doesNotExist())
//         .andExpect(jsonPath("$.[*].category.banner").doesNotExist())
//         .andExpect(jsonPath("$.[*].category.products").doesNotExist())
//         //
//         .andExpect(jsonPath("$.[*].images").isArray())
//         .andExpect(jsonPath("$.[*].images[*].id").isNotEmpty())
//         .andExpect(jsonPath("$.[*].images[*].imagePath").isNotEmpty())
//         .andExpect(jsonPath("$.[*].images[*].product").doesNotExist())
//         //
//         .andExpect(jsonPath("$.[*].variants").doesNotExist());
//   }
//
//   @Test
//   @Transactional
//   public void testGetValidProductDetail() throws Exception {
//     Product validProduct = productRepository.findAll().iterator().next();
//
//     restMockMvc
//         .perform(
//             get("/api/admin/products/" + validProduct.getId()).accept(MediaType.APPLICATION_JSON))
//         .andExpect(status().isOk())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//         .andExpect(jsonPath("$.id").value(validProduct.getId()))
//         .andExpect(jsonPath("$.name").value(validProduct.getName()))
//         .andExpect(jsonPath("$.ingredients").value(validProduct.getIngredients()))
//         .andExpect(jsonPath("$.allergens").value(validProduct.getAllergens()))
//         .andExpect(jsonPath("$.available").value(validProduct.isAvailable()))
//         //
//         .andExpect(jsonPath("$.category").exists())
//         .andExpect(jsonPath("$.category.id").value(validProduct.getId()))
//         .andExpect(jsonPath("$.category.name").value(validProduct.getCategory().getName()))
//         .andExpect(jsonPath("$.category.image").doesNotExist())
//         .andExpect(jsonPath("$.category.icon").doesNotExist())
//         .andExpect(jsonPath("$.category.banner").doesNotExist())
//         .andExpect(jsonPath("$.category.products").doesNotExist())
//         //
//         .andExpect(jsonPath("$.images").isArray())
//         .andExpect(jsonPath("$.images[*].id").isNotEmpty())
//         .andExpect(jsonPath("$.images[*].imagePath").isNotEmpty())
//         .andExpect(jsonPath("$.images[*].product").doesNotExist())
//         //
//         .andExpect(jsonPath("$.variants").doesNotExist());
//   }
//
//   @Test
//   @Transactional
//   public void testGetProductDetailWithNotExistingId() throws Exception {
//     restMockMvc
//         .perform(get("/api/admin/products/" + 0).accept(MediaType.APPLICATION_JSON))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testCreateValidProduct() throws Exception {
//     final long beforeCreationProductNum = productRepository.count();
//
//     final ProductCategory category = categoryRepository.findAll().iterator().next();
//
//     AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             .setName(TEST_PRODUCT_NAME)
//             .setIngredients(TEST_PRODUCT_INGREDIENTS)
//             .setAllergens(TEST_PRODUCT_ALLERGENS)
//             .setCategoryId(category.getId())
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isCreated())
//         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//         .andExpect(jsonPath("$.id").isNotEmpty())
//         .andExpect(jsonPath("$.name").value(adminProductCreateDTO.getName()))
//         .andExpect(jsonPath("$.ingredients").value(adminProductCreateDTO.getIngredients()))
//         .andExpect(jsonPath("$.allergens").value(adminProductCreateDTO.getAllergens()))
//         .andExpect(jsonPath("$.available").value(adminProductCreateDTO.isAvailable()))
//         //
//         .andExpect(jsonPath("$.category").exists())
//         .andExpect(jsonPath("$.category.id").value(category.getId()))
//         .andExpect(jsonPath("$.category.name").value(category.getName()))
//         .andExpect(jsonPath("$.category.image").doesNotExist())
//         .andExpect(jsonPath("$.category.icon").doesNotExist())
//         .andExpect(jsonPath("$.category.banner").doesNotExist())
//         .andExpect(jsonPath("$.category.products").doesNotExist())
//         //
//         .andExpect(jsonPath("$.images").isArray())
//         .andExpect(jsonPath("$.images").isEmpty())
//         //
//         .andExpect(jsonPath("$.variants").doesNotExist());
//
//     assertPersistedProducts(
//         products -> {
//           assertThat(products.size()).isEqualTo(beforeCreationProductNum + 1);
//           Product addedProduct = products.get(products.size() - 1);
//           assertThat(addedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(addedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(addedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(addedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//           assertThat(addedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(addedProduct.getImages()).hasSize(0);
//         });
//   }
//
//   @Test
//   @Transactional
//   public void testCreateProductWithNotEmptyId() throws Exception {
//     final ProductCategory category = categoryRepository.findAll().iterator().next();
//
//     AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(1000)
//             .setName(TEST_PRODUCT_NAME)
//             .setIngredients(TEST_PRODUCT_INGREDIENTS)
//             .setAllergens(TEST_PRODUCT_ALLERGENS)
//             .setCategoryId(category.getId())
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testCreateProductWithExistingName() throws Exception {
//     final ProductCategory category = categoryRepository.findAll().iterator().next();
//     final Product existingProduct = productRepository.findAll().iterator().next();
//
//     AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             .setName(existingProduct.getName())
//             .setIngredients(TEST_PRODUCT_INGREDIENTS)
//             .setAllergens(TEST_PRODUCT_ALLERGENS)
//             .setCategoryId(category.getId())
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testCreateProductWithEmptyName() throws Exception {
//     final ProductCategory category = categoryRepository.findAll().iterator().next();
//
//     AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             .setName("")
//             .setIngredients(TEST_PRODUCT_INGREDIENTS)
//             .setAllergens(TEST_PRODUCT_ALLERGENS)
//             .setCategoryId(category.getId())
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testCreateProductWithEmptyCategoryId() throws Exception {
//     AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             .setName(TEST_PRODUCT_NAME)
//             .setIngredients(TEST_PRODUCT_INGREDIENTS)
//             .setAllergens(TEST_PRODUCT_ALLERGENS)
//             .setCategoryId(null)
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testCreateProductWithNotExistingCategory() throws Exception {
//     final int notExistingCategoryId =
//         categoryRepository.findAll().stream().mapToInt(ProductCategory::getId).max().orElse(0) + 1;
//
//     AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             .setName(TEST_PRODUCT_NAME)
//             .setIngredients(TEST_PRODUCT_INGREDIENTS)
//             .setAllergens(TEST_PRODUCT_ALLERGENS)
//             .setCategoryId(notExistingCategoryId)
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateValidProductWithNoImageChange() throws Exception {
//     final Product validProduct = productRepository.findAll().iterator().next();
//     final int differentProductCategoryId =
//         categoryRepository.findAll().stream()
//             .filter(category -> !category.getId().equals(validProduct.getCategory().getId()))
//             .findFirst()
//             .orElseThrow()
//             .getId();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(validProduct.getId())
//             .setName("change-name")
//             .setIngredients("change-ingredients")
//             .setAllergens("change-allergens")
//             .setCategoryId(differentProductCategoryId)
//             .setAvailable(true);
//
//     restMockMvc
//         .perform(
//             post("/api/admin/products/update")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isOk());
//
//     assertPersistedProducts(
//         products -> {
//           Product updatedProduct =
//               products.stream()
//                   .filter(product -> product.getId().equals(validProduct.getId()))
//                   .findFirst()
//                   .orElseThrow();
//           assertThat(updatedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(updatedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(updatedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(updatedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(updatedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//           assertThat(updatedProduct.getImages()).hasSize(validProduct.getImages().size());
//           assertThat(productImageRepository.findByProduct(updatedProduct))
//               .hasSize(validProduct.getImages().size());
//           for (int idx = 0; idx < validProduct.getImages().size(); idx++) {
//             assertThat(updatedProduct.getImages().get(idx))
//                 .isEqualTo(validProduct.getImages().get(idx));
//             assertThat(fileService.productImageExist(validProduct.getImages().get(idx))).isTrue();
//           }
//         });
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductHasNoImageByAddOneImage() throws Exception {
//     final Product productHasNoImage =
//         productRepository.findById(TEST_PRODUCT_ID_DOES_NOT_HAS_IMAGE).orElseThrow();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasNoImage.getId())
//             .setName(productHasNoImage.getName())
//             .setIngredients(productHasNoImage.getIngredients())
//             .setAllergens(productHasNoImage.getAllergens())
//             .setCategoryId(productHasNoImage.getCategory().getId())
//             .setAvailable(productHasNoImage.isAvailable());
//
//     final MockMultipartFile multipartFile1 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_1.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_1.getInputStream().readAllBytes());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .file(multipartFile1)
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isOk());
//
//     assertPersistedProducts(
//         products -> {
//           Product updatedProduct =
//               products.stream()
//                   .filter(product -> product.getId().equals(productHasNoImage.getId()))
//                   .findFirst()
//                   .orElseThrow();
//           assertThat(updatedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(updatedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(updatedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(updatedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(updatedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//           assertThat(updatedProduct.getImages()).hasSize(1);
//           assertThat(productImageRepository.findByProduct(updatedProduct))
//               .hasSize(1)
//               .contains(updatedProduct.getImages().get(0));
//           for (int idx = 0; idx < productHasNoImage.getImages().size(); idx++) {
//             assertThat(updatedProduct.getImages().get(idx))
//                 .isEqualTo(productHasNoImage.getImages().get(idx));
//             assertThat(fileService.productImageExist(productHasNoImage.getImages().get(idx)))
//                 .isTrue();
//             // tear down tasks
//             fileService.deleteProductImage(updatedProduct.getImages().get(idx));
//           }
//         });
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductHasNoImageByAddMoreThanOneImage() throws Exception {
//     final Product productHasNoImage =
//         productRepository.findById(TEST_PRODUCT_ID_DOES_NOT_HAS_IMAGE).orElseThrow();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasNoImage.getId())
//             .setName(productHasNoImage.getName())
//             .setIngredients(productHasNoImage.getIngredients())
//             .setAllergens(productHasNoImage.getAllergens())
//             .setCategoryId(productHasNoImage.getCategory().getId())
//             .setAvailable(productHasNoImage.isAvailable());
//
//     final MockMultipartFile multipartFile1 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_1.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_1.getInputStream().readAllBytes());
//
//     final MockMultipartFile multipartFile2 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_2.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_2.getInputStream().readAllBytes());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .file(multipartFile1)
//                 .file(multipartFile2)
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isOk());
//
//     assertPersistedProducts(
//         products -> {
//           Product updatedProduct =
//               products.stream()
//                   .filter(product -> product.getId().equals(productHasNoImage.getId()))
//                   .findFirst()
//                   .orElseThrow();
//           assertThat(updatedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(updatedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(updatedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(updatedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(updatedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//           assertThat(updatedProduct.getImages()).hasSize(2);
//           assertThat(productImageRepository.findByProduct(updatedProduct))
//               .hasSize(2)
//               .contains(updatedProduct.getImages().get(0), updatedProduct.getImages().get(1));
//           for (int idx = 0; idx < productHasNoImage.getImages().size(); idx++) {
//             assertThat(updatedProduct.getImages().get(idx))
//                 .isEqualTo(productHasNoImage.getImages().get(idx));
//             assertThat(fileService.productImageExist(productHasNoImage.getImages().get(idx)))
//                 .isTrue();
//             // tear down tasks
//             fileService.deleteProductImage(updatedProduct.getImages().get(idx));
//           }
//         });
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductHasImagesByAddImages() throws Exception {
//     final Product productHasImages =
//         productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final List<ProductImage> oldProductImages =
//         productImageRepository.findByProduct(productHasImages);
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasImages.getId())
//             .setName(productHasImages.getName())
//             .setIngredients(productHasImages.getIngredients())
//             .setAllergens(productHasImages.getAllergens())
//             .setCategoryId(productHasImages.getCategory().getId())
//             .setAvailable(productHasImages.isAvailable());
//
//     final MockMultipartFile multipartFile1 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_1.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_1.getInputStream().readAllBytes());
//
//     final MockMultipartFile multipartFile2 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_2.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_2.getInputStream().readAllBytes());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .file(multipartFile1)
//                 .file(multipartFile2)
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isOk());
//
//     assertPersistedProducts(
//         products -> {
//           Product updatedProduct =
//               products.stream()
//                   .filter(product -> product.getId().equals(productHasImages.getId()))
//                   .findFirst()
//                   .orElseThrow();
//           assertThat(updatedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(updatedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(updatedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(updatedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(updatedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//           final int updatedProductImageNum = oldProductImages.size() + 2;
//           assertThat(updatedProduct.getImages()).hasSize(updatedProductImageNum);
//           assertThat(productImageRepository.findByProduct(updatedProduct))
//               .hasSize(updatedProductImageNum)
//               .containsExactly(
//                   updatedProduct.getImages().get(0),
//                   updatedProduct.getImages().get(1),
//                   updatedProduct.getImages().get(2),
//                   updatedProduct.getImages().get(3));
//           for (int idx = 0; idx < productHasImages.getImages().size(); idx++) {
//             assertThat(updatedProduct.getImages().get(idx))
//                 .isEqualTo(productHasImages.getImages().get(idx));
//             assertThat(fileService.productImageExist(productHasImages.getImages().get(idx)))
//                 .isTrue();
//           }
//           // tear down tasks
//           fileService.deleteProductImage(
//               updatedProduct.getImages().get(updatedProductImageNum - 2));
//           fileService.deleteProductImage(
//               updatedProduct.getImages().get(updatedProductImageNum - 1));
//         });
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductHasImagesByRemoveAndAddImages() throws Exception {
//     final Product productHasImages =
//         productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final List<ProductImage> oldProductImages =
//         productImageRepository.findByProduct(productHasImages);
//
//     ProductImage deletedImage = oldProductImages.get(0);
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasImages.getId())
//             .setName(productHasImages.getName())
//             .setIngredients(productHasImages.getIngredients())
//             .setAllergens(productHasImages.getAllergens())
//             .setCategoryId(productHasImages.getCategory().getId())
//             .setAvailable(productHasImages.isAvailable());
//     // .setDeletedImageIds(List.of(deletedImage.getId()));
//
//     final MockMultipartFile multipartFile1 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_1.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_1.getInputStream().readAllBytes());
//
//     final MockMultipartFile multipartFile2 =
//         new MockMultipartFile(
//             "addedImages",
//             TEST_PRODUCT_IMAGE_2.getFilename(),
//             MediaType.IMAGE_JPEG_VALUE,
//             TEST_PRODUCT_IMAGE_2.getInputStream().readAllBytes());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .file(multipartFile1)
//                 .file(multipartFile2)
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isOk());
//
//     // Assert that deletedImage should be deleted
//     assertThat(productImageRepository.findById(deletedImage.getId())).isEmpty();
//     assertThat(fileService.productImageExist(deletedImage)).isFalse();
//
//     assertPersistedProducts(
//         products -> {
//           Product updatedProduct =
//               products.stream()
//                   .filter(product -> product.getId().equals(productHasImages.getId()))
//                   .findFirst()
//                   .orElseThrow();
//           assertThat(updatedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(updatedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(updatedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(updatedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(updatedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//
//           final int updatedProductImageNum = oldProductImages.size() - 1 + 2;
//           assertThat(updatedProduct.getImages()).hasSize(updatedProductImageNum);
//           assertThat(productImageRepository.findByProduct(updatedProduct))
//               .hasSize(updatedProductImageNum)
//               .containsExactly(
//                   updatedProduct.getImages().get(0),
//                   updatedProduct.getImages().get(1),
//                   updatedProduct.getImages().get(2));
//           for (int idx = 0; idx < productHasImages.getImages().size(); idx++) {
//             assertThat(updatedProduct.getImages().get(idx))
//                 .isEqualTo(productHasImages.getImages().get(idx));
//             assertThat(fileService.productImageExist(productHasImages.getImages().get(idx)))
//                 .isTrue();
//           }
//           // tear down tasks
//           fileService.deleteProductImage(
//               updatedProduct.getImages().get(updatedProductImageNum - 2));
//           fileService.deleteProductImage(
//               updatedProduct.getImages().get(updatedProductImageNum - 1));
//         });
//
//     // restore old product image file
//     fileService.saveFile(
//         new ClassPathResource(deletedImage.getImagePath(), AdminProductsResourceIT.class),
//         fileService.resolveProductImageFilePath(deletedImage));
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductHasImagesByRemoveAllImages() throws Exception {
//     final Product productHasImages =
//         productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final List<ProductImage> oldProductImages =
//         productImageRepository.findByProduct(productHasImages);
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasImages.getId())
//             .setName(productHasImages.getName())
//             .setIngredients(productHasImages.getIngredients())
//             .setAllergens(productHasImages.getAllergens())
//             .setCategoryId(productHasImages.getCategory().getId())
//             .setAvailable(productHasImages.isAvailable());
//     // .setDeletedImageIds(oldProductImages.stream().map(ProductImage::getId).collect(Collectors.toList()));
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isOk());
//
//     // Assert that old images should be deleted
//     oldProductImages.forEach(
//         deletedImage -> {
//           assertThat(productImageRepository.findById(deletedImage.getId())).isEmpty();
//           assertThat(fileService.productImageExist(deletedImage)).isFalse();
//         });
//
//     assertPersistedProducts(
//         products -> {
//           Product updatedProduct =
//               products.stream()
//                   .filter(product -> product.getId().equals(productHasImages.getId()))
//                   .findFirst()
//                   .orElseThrow();
//           assertThat(updatedProduct.getName()).isEqualTo(adminProductCreateDTO.getName());
//           assertThat(updatedProduct.getIngredients())
//               .isEqualTo(adminProductCreateDTO.getIngredients());
//           assertThat(updatedProduct.getAllergens()).isEqualTo(adminProductCreateDTO.getAllergens());
//           assertThat(updatedProduct.getCategory().getId())
//               .isEqualTo(adminProductCreateDTO.getCategoryId());
//           assertThat(updatedProduct.isAvailable()).isEqualTo(adminProductCreateDTO.isAvailable());
//
//           assertThat(updatedProduct.getImages()).hasSize(0);
//           assertThat(productImageRepository.findByProduct(updatedProduct)).hasSize(0);
//         });
//
//     // restore old product image files
//     oldProductImages.forEach(
//         productImage ->
//             fileService.saveFile(
//                 new ClassPathResource(productImage.getImagePath(), AdminProductsResourceIT.class),
//                 fileService.resolveProductImageFilePath(productImage)));
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductByDeleteImageButHasNoExistId() throws Exception {
//     final Product productHasImages =
//         productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final int notExistingImageId =
//         productImageRepository.findAll().stream().mapToInt(ProductImage::getId).max().orElse(0) + 1;
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasImages.getId())
//             .setName(productHasImages.getName())
//             .setIngredients(productHasImages.getIngredients())
//             .setAllergens(productHasImages.getAllergens())
//             .setCategoryId(productHasImages.getCategory().getId())
//             .setAvailable(productHasImages.isAvailable());
//     // .setDeletedImageIds(List.of(productHasImages.getImages().get(0).getId(),
//     // notExistingImageId)); // add not existing image id
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//
//     // Assert that old images should not be deleted
//     productHasImages
//         .getImages()
//         .forEach(
//             image -> {
//               assertThat(productImageRepository.findById(image.getId())).isPresent();
//               assertThat(fileService.productImageExist(image)).isTrue();
//             });
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductByDeleteImageButInvalidId() throws Exception {
//     final Product productHasImages =
//         productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final int imageIdNotOwnedByProduct =
//         productImageRepository.findAll().stream()
//             .filter(image -> !image.getProduct().getId().equals(productHasImages.getId()))
//             .findFirst()
//             .orElseThrow()
//             .getId();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(productHasImages.getId())
//             .setName(productHasImages.getName())
//             .setIngredients(productHasImages.getIngredients())
//             .setAllergens(productHasImages.getAllergens())
//             .setCategoryId(productHasImages.getCategory().getId())
//             .setAvailable(productHasImages.isAvailable());
//     // .setDeletedImageIds(List.of(productHasImages.getImages().get(0).getId(),
//     // imageIdNotOwnedByProduct)); // add not existing image id
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 // why: Set Content Type = MediaType.MULTIPART_FORM_DATA_VALUE
//                 //  thì test fail?
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//
//     // Assert that old images should not be deleted
//     productHasImages
//         .getImages()
//         .forEach(
//             image -> {
//               assertThat(productImageRepository.findById(image.getId())).isPresent();
//               assertThat(fileService.productImageExist(image)).isTrue();
//             });
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductWithEmptyId() throws Exception {
//     final Product product = productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(null) // empty id
//             .setName(product.getName())
//             .setIngredients(product.getIngredients())
//             .setCategoryId(categoryRepository.findAll().iterator().next().getId())
//             .setAvailable(product.isAvailable());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductWithNotExistingId() throws Exception {
//     final Product product = productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final int notExistingProductId =
//         StreamSupport.stream(productRepository.findAll().spliterator(), false)
//                 .mapToInt(Product::getId)
//                 .max()
//                 .orElse(0)
//             + 1;
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(notExistingProductId) // empty id
//             .setName(product.getName())
//             .setIngredients(product.getIngredients())
//             .setCategoryId(categoryRepository.findAll().iterator().next().getId())
//             .setAvailable(product.isAvailable());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductWithEmptyName() throws Exception {
//     final Product product = productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(product.getId())
//             .setName(null)
//             .setIngredients(product.getIngredients())
//             .setCategoryId(categoryRepository.findAll().iterator().next().getId())
//             .setAvailable(product.isAvailable());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductWithExistingName() throws Exception {
//     final Product product = productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//     final String existingProductName =
//         StreamSupport.stream(productRepository.findAll().spliterator(), false)
//             .filter(p -> !p.getName().equals(product.getName()))
//             .findFirst()
//             .orElseThrow()
//             .getName();
//
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(product.getId())
//             .setName(existingProductName)
//             .setIngredients(product.getIngredients())
//             .setCategoryId(categoryRepository.findAll().iterator().next().getId())
//             .setAvailable(product.isAvailable());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   @Test
//   @Transactional
//   public void testUpdateProductWithNotExistingCategory() throws Exception {
//     final Product product = productRepository.findById(TEST_PRODUCT_ID_HAS_IMAGE).orElseThrow();
//     final int notExistingCategoryId =
//         categoryRepository.findAll().stream().mapToInt(ProductCategory::getId).max().orElse(0) + 1;
//     final AdminProductCreateDTO adminProductCreateDTO =
//         new AdminProductCreateDTO()
//             // .setId(product.getId())
//             .setName(product.getName())
//             .setIngredients(product.getIngredients())
//             .setCategoryId(notExistingCategoryId)
//             .setAvailable(product.isAvailable());
//
//     restMockMvc
//         .perform(
//             MockMvcRequestBuilders.multipart("/api/admin/products/update")
//                 .accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(TestUtil.convertObjectToJsonBytes(adminProductCreateDTO)))
//         .andExpect(status().isBadRequest());
//   }
//
//   private void assertPersistedProducts(Consumer<List<Product>> productAssertion) {
//     productAssertion.accept(
//         StreamSupport.stream(productRepository.findAll().spliterator(), false)
//             .collect(Collectors.toList()));
//   }
// }
