package com.bakery.shop.web.rest.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductType;
import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.repository.order.OrderDetailRepository;
import com.bakery.shop.repository.product.ProductRepository;
import com.bakery.shop.repository.product.ProductTypeRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.security.AuthoritiesConstants;
import com.bakery.shop.service.dto.admin.product.AdminVariantDTO;
import com.bakery.shop.web.rest.AdminProductsResource;
import com.bakery.shop.web.rest.TestUtil;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for the {@link AdminProductsResource} REST controller. */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
public class AdminVariantsResourceIT {

    private static final int TEST_PRODUCT_ID_VALID = 35;
    private static final int TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT = 6;
    private static final long TEST_VALID_COST = 10000;
    private static final long TEST_VALID_PRICE = 20000;

    @Autowired
    private ProductVariantRepository variantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository typeRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private MockMvc restMockMvc;

    @Test
    @Transactional
    public void testCreateValidVariant() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.product").isNotEmpty())
            .andExpect(jsonPath("$.productType").isNotEmpty())
            .andExpect(jsonPath("$.cost").value(adminVariantDTO.getCost()))
            .andExpect(jsonPath("$.price").value(adminVariantDTO.getPrice()))
            .andExpect(jsonPath("$.hot").value(adminVariantDTO.isHot()))
            .andExpect(jsonPath("$.available").value(adminVariantDTO.isAvailable()));

        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum + 1);
            ProductVariant addedVariant = variants.get(variants.size() - 1);
            assertThat(addedVariant.getProduct().getId()).isEqualTo(adminVariantDTO.getProductId());
            assertThat(addedVariant.getProductType().getId()).isEqualTo(adminVariantDTO.getTypeId());
            assertThat(addedVariant.getCost()).isEqualTo(adminVariantDTO.getCost());
            assertThat(addedVariant.getPrice()).isEqualTo(adminVariantDTO.getPrice());
            assertThat(addedVariant.isHot()).isEqualTo(adminVariantDTO.isHot());
            assertThat(addedVariant.isAvailable()).isEqualTo(adminVariantDTO.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testCreateVariantWithNotEmptyId() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(1000)
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testCreateVariantWithEmptyProductId() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(null)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testCreateVariantWithNotExistingProductId() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        final int notExistingProductId =
            StreamSupport.stream(productRepository.findAll().spliterator(), false).mapToInt(Product::getId).max().orElse(0) + 1;

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(notExistingProductId)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testCreateVariantWithEmptyProductTypeId() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(null) // null type id
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testCreateVariantWithNotExistingProductTypeId() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        final int notExistingTypeId = typeRepository.findAll().stream().mapToInt(ProductType::getId).max().orElse(0) + 1;

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(notExistingTypeId)
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testCreateVariantViolateUKProductAndProductType() throws Exception {
        final ProductVariant existingVariant = variantRepository.findAll().iterator().next();

        final int beforeCreationVariantNum = variantRepository.findAll().size();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(existingVariant.getProduct().getId())
            .setTypeId(existingVariant.getProductType().getId())
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testCreateVariantWithInvalidCostAndPrice() throws Exception {
        final int beforeCreationVariantNum = variantRepository.findAll().size();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(30000) // invalid cost and price
            .setPrice(10000) //
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                post("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertPersistedVariant(variants -> {
            assertThat(variants.size()).isEqualTo(beforeCreationVariantNum);
        });
    }

    @Test
    @Transactional
    public void testUpdateValidVariant() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(variant.getId())
            .setProductId(variant.getProduct().getId())
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(TEST_VALID_COST)
            .setPrice(TEST_VALID_PRICE)
            .setAvailable(true)
            .setHot(false);

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(adminVariantDTO.getProductId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(adminVariantDTO.getTypeId());
            assertThat(updatedVariant.getCost()).isEqualTo(adminVariantDTO.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(adminVariantDTO.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(adminVariantDTO.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(adminVariantDTO.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithEmptyId() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(null) // empty id
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(variant.getCost() + 1000)
            .setPrice(variant.getPrice() + 1000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithNotExistingId() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();
        final int notExistingVariantId = variantRepository.findAll().stream().mapToInt(ProductVariant::getId).max().orElse(0) + 1;
        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(notExistingVariantId) // not existing variant id
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(variant.getCost() + 1000)
            .setPrice(variant.getPrice() + 1000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithEmptyProductId() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(variant.getId())
            .setProductId(null) // empty product id
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(variant.getCost() + 1000)
            .setPrice(variant.getPrice() + 1000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithNotExistingProductId() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();
        final int notExistingProductId =
            StreamSupport.stream(productRepository.findAll().spliterator(), false).mapToInt(Product::getId).max().orElse(0) + 1;
        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(variant.getId())
            .setProductId(notExistingProductId) // not existing product id
            .setTypeId(TEST_TYPE_ID_DOES_NOT_HAVE_PRODUCT)
            .setCost(variant.getCost() + 1000)
            .setPrice(variant.getPrice() + 1000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithEmptyProductTypeId() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(variant.getId())
            .setProductId(TEST_PRODUCT_ID_VALID)
            .setTypeId(null) // empty product type id
            .setCost(variant.getCost() + 1000)
            .setPrice(variant.getPrice() + 1000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithNotExistingProductTypeId() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();
        final int notExistingTypeId = typeRepository.findAll().stream().mapToInt(ProductType::getId).max().orElse(0) + 1;

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(variant.getId())
            .setProductId(variant.getProduct().getId())
            .setTypeId(notExistingTypeId) // not existing
            // product type id
            .setCost(variant.getCost() + 1000)
            .setPrice(variant.getPrice() + 1000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testUpdateVariantWithInvalidCostAndPrice() throws Exception {
        final ProductVariant variant = variantRepository.findAll().iterator().next();

        AdminVariantDTO adminVariantDTO = new AdminVariantDTO()
            .setId(variant.getId())
            .setProductId(variant.getProduct().getId())
            .setTypeId(variant.getProductType().getId())
            .setCost(100000)
            .setPrice(100000)
            .setAvailable(!variant.isAvailable())
            .setHot(!variant.isHot());

        restMockMvc
            .perform(
                put("/api/admin/variants")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminVariantDTO))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertPersistedVariant(variants -> {
            ProductVariant updatedVariant = variants.stream().filter(v -> v.getId().equals(variant.getId())).findFirst().orElseThrow();
            assertThat(updatedVariant.getProduct().getId()).isEqualTo(variant.getProduct().getId());
            assertThat(updatedVariant.getProductType().getId()).isEqualTo(variant.getProductType().getId());
            assertThat(updatedVariant.getCost()).isEqualTo(variant.getCost());
            assertThat(updatedVariant.getPrice()).isEqualTo(variant.getPrice());
            assertThat(updatedVariant.isHot()).isEqualTo(variant.isHot());
            assertThat(updatedVariant.isAvailable()).isEqualTo(variant.isAvailable());
        });
    }

    @Test
    @Transactional
    public void testDeleteValidVariant() throws Exception {
        final int canDeleteVariantId = variantRepository
            .findAll()
            .stream()
            .filter(variant ->
                orderDetailRepository
                    .findAll()
                    .stream()
                    .noneMatch(orderDetail -> orderDetail.getId().getVariant().getId().equals(variant.getId()))
            )
            .findFirst()
            .orElseThrow()
            .getId();
        restMockMvc
            .perform(
                delete("/api/admin/variants")
                    .param("variantId", String.valueOf(canDeleteVariantId))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
        assertThat(variantRepository.findById(canDeleteVariantId)).isEmpty();
    }

    @Test
    @Transactional
    public void testDeleteVariantWithNotExistId() throws Exception {
        final int notExitingVariantId = variantRepository.findAll().stream().mapToInt(ProductVariant::getId).max().orElse(0) + 1;
        final int beforeDeleteVariantNum = variantRepository.findAll().size();
        restMockMvc
            .perform(
                delete("/api/admin/variants")
                    .param("variantId", String.valueOf(notExitingVariantId))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertThat(variantRepository.count()).isEqualTo(beforeDeleteVariantNum);
    }

    @Test
    @Transactional
    public void testDeleteVariantAlreadySold() throws Exception {
        final int soldVariantId = orderDetailRepository.findAll().iterator().next().getId().getVariant().getId();

        final int beforeDeleteVariantNum = variantRepository.findAll().size();
        restMockMvc
            .perform(
                delete("/api/admin/variants")
                    .param("variantId", String.valueOf(soldVariantId))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        assertThat(variantRepository.count()).isEqualTo(beforeDeleteVariantNum);
    }

    private void assertPersistedVariant(Consumer<List<ProductVariant>> variantAssertion) {
        variantAssertion.accept(variantRepository.findAll());
    }
}
