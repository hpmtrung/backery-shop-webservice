package com.bakery.shop.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.repository.order.OrderDetailRepository;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.CartService;
import com.bakery.shop.service.dto.user.UserCheckOutInitDTO;
import com.bakery.shop.web.rest.TestUtil;
import com.bakery.shop.web.rest.UserCartResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for the {@link UserCartResource} REST controller. */
@AutoConfigureMockMvc
@IntegrationTest
public class UserCartResourceIT {

    private static final String TEST_USER_EMAIL = "user@gmail.com";
    private static final String TEST_USER_EMAIL_WITH_EMPTY_CART = "nvan@gmail.com";
    private static final int TEST_VALID_NON_EXIST_VARIANT_ID = 10;
    private static final int TEST_VALID_EXIST_VARIANT_ID = 2;
    private static final int TEST_VALID_VARIANT_QUANTITY = 2;
    private static final int TEST_INVALID_VARIANT_QUANTITY = 0;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductVariantRepository variantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private MockMvc restMockMvc;

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    void testGetLoginUserCart() throws Exception {
        restMockMvc
            .perform(get("/api/user/cart").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.cartItems").isArray())
            .andExpect(jsonPath("$.cartItems").value(hasSize(2)))
            .andExpect(jsonPath("$.cartItems[0].variantId").value(is(2)))
            .andExpect(jsonPath("$.cartItems[0].productName").value(is("Bánh Cupcake Hoa Hồng")))
            .andExpect(jsonPath("$.cartItems[0].typeName").value(is("Bộ 6")))
            .andExpect(jsonPath("$.cartItems[0].price").value(is(200000)))
            .andExpect(jsonPath("$.cartItems[0].quantity").value(is(1)))
            .andExpect(jsonPath("$.cartItems[1].variantId").value(is(4)))
            .andExpect(jsonPath("$.cartItems[1].productName").value(is("Bánh Cupcake Hoa Hồng")))
            .andExpect(jsonPath("$.cartItems[1].typeName").value(is("Bộ 9")))
            .andExpect(jsonPath("$.cartItems[1].price").value(is(220000)))
            .andExpect(jsonPath("$.cartItems[1].quantity").value(is(2)));
    }

    // @Test
    // @WithMockUser(TEST_USER_EMAIL)
    // @Transactional
    // void testAddItemToCart() throws Exception {
    //     final int loginUserCartItemsNum = cartService.getAccountCart().getItems().size();
    //     final String addedCartItemJsonPath = "$.cartItems[" + loginUserCartItemsNum + "]";
    //     final ProductVariant selectedVariant = variantRepository.findById(TEST_VALID_NON_EXIST_VARIANT_ID).orElseThrow();
    //     restMockMvc
    //         .perform(
    //             post("/api/user/cart/add-item")
    //                 .param("variantId", String.valueOf(TEST_VALID_NON_EXIST_VARIANT_ID))
    //                 .param("quantity", String.valueOf(TEST_VALID_VARIANT_QUANTITY))
    //                 .accept(MediaType.APPLICATION_JSON)
    //         )
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.cartItems").value(hasSize(loginUserCartItemsNum + 1)))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".variantId")).value(is(TEST_VALID_NON_EXIST_VARIANT_ID)))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".productName")).value(is(selectedVariant.getProduct().getName())))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".typeName")).value(is(selectedVariant.getProductType().getName())))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".price")).value(is(selectedVariant.getPrice())))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".quantity")).value(is(TEST_VALID_VARIANT_QUANTITY)));
    // }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    public void testAddExistItemToCart() throws Exception {
        final int loginUserCartItemNum = accountService
            .getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL)
            .orElseThrow()
            .getCartDetails()
            .size();

        restMockMvc
            .perform(
                post("/api/user/cart/add-item")
                    .param("variantId", String.valueOf(TEST_VALID_EXIST_VARIANT_ID))
                    .param("quantity", String.valueOf(TEST_VALID_VARIANT_QUANTITY))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertThat(accountService.getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL).orElseThrow().getCartDetails().size())
            .isEqualTo(loginUserCartItemNum);
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    public void testAddItemToCartWithInvalidQuantity() throws Exception {
        final int loginUserCartItemNum = accountService
            .getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL)
            .orElseThrow()
            .getCartDetails()
            .size();

        restMockMvc
            .perform(
                post("/api/user/cart/add-item")
                    .param("variantId", String.valueOf(TEST_VALID_NON_EXIST_VARIANT_ID))
                    .param("quantity", String.valueOf(TEST_INVALID_VARIANT_QUANTITY))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertThat(accountService.getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL).orElseThrow().getCartDetails().size())
            .isEqualTo(loginUserCartItemNum);
    }

    // @Test
    // @WithMockUser(TEST_USER_EMAIL)
    // @Transactional
    // void testUpdateValidItemInCart() throws Exception {
    //     final int loginUserCartItemsNum = cartService.getAccountCart().getItems().size();
    //     int oldQuantity = -1, updateCartItemIdx = -1;
    //     List<CartDetail> userCartDetails = accountService.getLoginAccountWithAuthorities().orElseThrow().getCartDetails();
    //     for (int itemIdx = 0; itemIdx < userCartDetails.size(); itemIdx++) {
    //         if (userCartDetails.get(itemIdx).getId().getVariant().getId().equals(TEST_VALID_EXIST_VARIANT_ID)) {
    //             oldQuantity = userCartDetails.get(itemIdx).getQuantity();
    //             updateCartItemIdx = itemIdx;
    //             break;
    //         }
    //     }
    //
    //     assertThat(oldQuantity).isNotEqualTo(-1);
    //     assertThat(updateCartItemIdx).isNotEqualTo(-1);
    //
    //     final int newQuantity = oldQuantity + 2;
    //
    //     final String addedCartItemJsonPath = "$.cartItems[" + updateCartItemIdx + "]";
    //     final ProductVariant selectedVariant = variantRepository.findById(TEST_VALID_EXIST_VARIANT_ID).orElseThrow();
    //
    //     restMockMvc
    //         .perform(
    //             post("/api/user/cart/update-item")
    //                 .param("variantId", String.valueOf(TEST_VALID_EXIST_VARIANT_ID))
    //                 .param("quantity", String.valueOf(newQuantity))
    //                 .accept(MediaType.APPLICATION_JSON)
    //         )
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.cartItems").value(hasSize(loginUserCartItemsNum)))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".variantId")).value(is(TEST_VALID_EXIST_VARIANT_ID)))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".productName")).value(is(selectedVariant.getProduct().getName())))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".typeName")).value(is(selectedVariant.getProductType().getName())))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".price")).value(is(selectedVariant.getPrice())))
    //         .andExpect(jsonPath(addedCartItemJsonPath.concat(".quantity")).value(is(newQuantity)));
    // }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    void testUpdateNonExistItemInCart() throws Exception {
        restMockMvc
            .perform(
                post("/api/user/cart/update-item")
                    .param("variantId", String.valueOf(TEST_VALID_NON_EXIST_VARIANT_ID))
                    .param("quantity", String.valueOf(TEST_VALID_VARIANT_QUANTITY))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    void testDeleteValidItemFromCart() throws Exception {
        final int loginUserCartItemsNum = accountService
            .getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL)
            .orElseThrow()
            .getCartDetails()
            .size();

        restMockMvc
            .perform(
                post("/api/user/cart/delete-item")
                    .param("variantId", String.valueOf(TEST_VALID_EXIST_VARIANT_ID))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.cartItems").value(hasSize(loginUserCartItemsNum - 1)));

        assertThat(
            accountService
                .getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL)
                .orElseThrow()
                .getCartDetails()
                .stream()
                .noneMatch(cartDetail -> cartDetail.getId().getVariant().getId().equals(TEST_VALID_EXIST_VARIANT_ID))
        )
            .isTrue();
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    void testDeleteNonExistItemFromCart() throws Exception {
        final int loginUserCartItemsNum = accountService
            .getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL)
            .orElseThrow()
            .getCartDetails()
            .size();

        restMockMvc
            .perform(
                post("/api/user/cart/delete-item")
                    .param("variantId", String.valueOf(TEST_VALID_NON_EXIST_VARIANT_ID))
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());

        assertThat(accountService.getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL).orElseThrow().getCartDetails().size())
            .isEqualTo(loginUserCartItemsNum);
    }

    // @Test
    // @WithMockUser(TEST_USER_EMAIL)
    // @Transactional
    // void testCheckOutValidCart() throws Exception {
    //     final Account loginUser = accountService.getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL).orElseThrow();
    //
    //     final List<CartDetail> beforeCheckoutCartDetails = List.copyOf(loginUser.getCartDetails());
    //     final long beforeCheckoutOrdersNum = orderRepository.count();
    //
    //     final UserCheckOutInitDTO checkOutInitDTO = new UserCheckOutInitDTO();
    //     checkOutInitDTO.setAddress("test-address");
    //     checkOutInitDTO.setNote("test-note");
    //     checkOutInitDTO.setPaidByCash(true);
    //
    //     final UserCartInfoDTO beforeCheckoutCartInfoDTO = cartService.getUserCartInfo(loginUser);
    //
    //     MvcResult result = restMockMvc
    //         .perform(
    //             post("/api/user/cart/checkout")
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(checkOutInitDTO))
    //         )
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.orderId").isNotEmpty())
    //         .andExpect(jsonPath("$.orderId").isNumber())
    //         .andReturn();
    //
    //     // After checkout, user's cart is empty
    //     assertThat(loginUser.getCartDetails().size()).isEqualTo(0);
    //
    //     final long orderId =
    //         JsonPath.read(result.getResponse().getContentAsString(), "$.orderId");
    //     assertThat(orderRepository.count()).isEqualTo(beforeCheckoutOrdersNum + 1);
    //
    //     Order order = orderRepository.findById(orderId).orElse(null);
    //     assertThat(order).isNotNull();
    //     assertThat(order.getAddress()).isEqualTo(checkOutInitDTO.getAddress());
    //     assertThat(order.getNote()).isEqualTo(checkOutInitDTO.getNote());
    //     assertThat(order.isPaidByCash()).isTrue();
    //     assertThat(order.getOrderedBy()).isEqualTo(accountService.getAccountWithAuthoritiesByEmail(TEST_USER_EMAIL).orElseThrow());
    //     assertThat(order.getStatus()).isEqualTo(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow());
    //     assertThat(order.getTotal()).isEqualTo(beforeCheckoutCartInfoDTO.getTotal());
    //     assertThat(order.getProfit()).isEqualTo(beforeCheckoutCartInfoDTO.getProfit());
    //
    //     List<OverviewOrderDetail> saveOrderDetails = orderDetailRepository.findAllById_Order(order);
    //     assertThat(saveOrderDetails.size()).isEqualTo(beforeCheckoutCartDetails.size());
    //     for (int i = 0; i < saveOrderDetails.size(); i++) {
    //         CartDetail beforeCheckoutCartDetail = beforeCheckoutCartDetails.get(i);
    //         OverviewOrderDetail saveOrderDetail = saveOrderDetails.get(i);
    //         assertThat(saveOrderDetail.getId().getVariant().getId()).isEqualTo(beforeCheckoutCartDetail.getId().getVariant().getId());
    //         assertThat(saveOrderDetail.getQuantity()).isEqualTo(beforeCheckoutCartDetail.getQuantity());
    //         assertThat(saveOrderDetail.getUnitPrice())
    //             .isEqualTo(beforeCheckoutCartDetail.getId().getVariant().getPrice());
    //     }
    // }

    @Test
    @WithMockUser(TEST_USER_EMAIL_WITH_EMPTY_CART)
    @Transactional
    void testCheckOutInValidCart() throws Exception {
        final UserCheckOutInitDTO checkOutInitDTO = new UserCheckOutInitDTO();
        checkOutInitDTO.setAddress("test-address");
        checkOutInitDTO.setNote("test-note");
        checkOutInitDTO.setPaidByCash(true);

        final long beforeCheckoutOrdersNum = orderRepository.count();

        restMockMvc
            .perform(
                post("/api/user/cart/checkout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkOutInitDTO))
            )
            .andExpect(status().isBadRequest());

        assertThat(orderRepository.count()).isEqualTo(beforeCheckoutOrdersNum);
    }
}
