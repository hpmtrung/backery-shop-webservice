package com.bakery.shop.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.web.rest.UserOrdersResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for the {@link UserOrdersResource} REST controller. */
@AutoConfigureMockMvc
@IntegrationTest
public class UserOrderResourceIT {

    private static final String TEST_USER_EMAIL = "ht10@gmail.com";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private MockMvc restMockMvc;

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    public void testGetLoginUserOrdersWithoutPaging() throws Exception {
        restMockMvc
            .perform(get("/api/user/orders").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(2)))
            .andExpect(jsonPath("$.orders[0].id").value(18))
            .andExpect(jsonPath("$.orders[0].createdAt").value("2021-12-13T05:55:06Z"))
            .andExpect(jsonPath("$.orders[0].statusId").value(OrderStatusConstants.PROCESSING.getId()))
            .andExpect(
                jsonPath("$.orders[0].statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow().getName())
            )
            .andExpect(jsonPath("$.orders[0].total").value(340000));
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    public void testGetLoginUserOrdersWithPaging() throws Exception {
        restMockMvc
            .perform(get("/api/user/orders").param("page", "0").param("size", "1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(1)))
            .andExpect(jsonPath("$.orders[0].id").value(18))
            .andExpect(jsonPath("$.orders[0].createdAt").value("2021-12-13T05:55:06Z"))
            .andExpect(jsonPath("$.orders[0].statusId").value(OrderStatusConstants.PROCESSING.getId()))
            .andExpect(
                jsonPath("$.orders[0].statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow().getName())
            )
            .andExpect(jsonPath("$.orders[0].total").value(340000));

        restMockMvc
            .perform(get("/api/user/orders").param("page", "1").param("size", "1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(1)))
            .andExpect(jsonPath("$.orders[0].id").value(33))
            .andExpect(jsonPath("$.orders[0].createdAt").value("2022-03-27T14:26:26Z"))
            .andExpect(jsonPath("$.orders[0].statusId").value(OrderStatusConstants.CANCEL.getId()))
            .andExpect(
                jsonPath("$.orders[0].statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow().getName())
            )
            .andExpect(jsonPath("$.orders[0].total").value(460000));
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    public void testGetLoginUserOrdersWithPagingOutsideLimit() throws Exception {
        restMockMvc
            .perform(get("/api/user/orders").param("page", "3").param("size", "1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(0)));
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    public void testGetLoginUserOrdersFilterByStatusWithoutPaging() throws Exception {
        restMockMvc
            .perform(get("/api/user/orders/status/" + OrderStatusConstants.PROCESSING.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(1)))
            .andExpect(jsonPath("$.orders[0].id").value(18))
            .andExpect(jsonPath("$.orders[0].statusId").value(OrderStatusConstants.PROCESSING.getId()))
            .andExpect(
                jsonPath("$.orders[0].statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow().getName())
            );

        restMockMvc
            .perform(get("/api/user/orders/status/" + OrderStatusConstants.CANCEL.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(1)))
            .andExpect(jsonPath("$.orders[0].id").value(33))
            .andExpect(jsonPath("$.orders[0].statusId").value(OrderStatusConstants.CANCEL.getId()))
            .andExpect(
                jsonPath("$.orders[0].statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow().getName())
            );

        restMockMvc
            .perform(get("/api/user/orders/status/" + OrderStatusConstants.DISPATCH.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(0)));
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    public void testGetLoginUserOrdersFilterByStatusWithPaging() throws Exception {
        restMockMvc
            .perform(
                get("/api/user/orders/status/" + OrderStatusConstants.PROCESSING.getId())
                    .param("page", "0")
                    .param("size", "1")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(1)))
            .andExpect(jsonPath("$.orders[0].id").value(18))
            .andExpect(jsonPath("$.orders[0].statusId").value(OrderStatusConstants.PROCESSING.getId()))
            .andExpect(
                jsonPath("$.orders[0].statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow().getName())
            );
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    public void testGetLoginUserOrdersFilterByStatusWithPagingOutsideLimit() throws Exception {
        restMockMvc
            .perform(
                get("/api/user/orders/status/" + OrderStatusConstants.PROCESSING.getId())
                    .param("page", "2")
                    .param("size", "1")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orders").exists())
            .andExpect(jsonPath("$.orders").isArray())
            .andExpect(jsonPath("$.orders").value(hasSize(0)));
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional // Why ?
    public void testGetDetailsValidOrder() throws Exception {
        final int orderId = 18;
        restMockMvc
            .perform(get("/api/user/orders/detail/" + orderId).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderId))
            .andExpect(jsonPath("$.createdAt").value("2021-12-13T05:55:06Z"))
            .andExpect(jsonPath("$.statusId").value(OrderStatusConstants.PROCESSING.getId()))
            .andExpect(
                jsonPath("$.statusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow().getName())
            )
            .andExpect(jsonPath("$.total").value(340000))
            .andExpect(jsonPath("$.canCancel").value(true))
            .andExpect(jsonPath("$.details").isArray())
            .andExpect(jsonPath("$.details").value(hasSize(2)))
            .andExpect(jsonPath("$.details[0].productName").value("Bánh Cupcake Hoa Hồng"))
            .andExpect(jsonPath("$.details[0].productImageUrl").value("1637281686544.jpg"))
            .andExpect(jsonPath("$.details[0].productTypeName").value("Bộ 6"))
            .andExpect(jsonPath("$.details[0].quantity").value(1))
            .andExpect(jsonPath("$.details[0].unitPrice").value(200000));
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    public void testGetDetailsInvalidOrder() throws Exception {
        restMockMvc.perform(get("/api/user/orders/detail/" + 1).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    public void testCancelValidOrder() throws Exception {
        final long validOrderId = 18L;
        restMockMvc.perform(post("/api/user/orders/cancel/" + validOrderId).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        Order order = orderRepository.findById(validOrderId).orElse(null);
        assertThat(order).isNotNull();
        assertThat(order.getStatus()).isEqualTo(orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow());
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    public void testCancelNonExistOrder() throws Exception {
        restMockMvc.perform(post("/api/user/orders/cancel/" + 1).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(TEST_USER_EMAIL)
    @Transactional
    public void testCancelInvalidOrder() throws Exception {
        final long invalidOrderId = 33L;
        final Order order = orderRepository.findById(invalidOrderId).orElse(null);
        assertThat(order).isNotNull();
        final int oldStatusId = order.getStatus().getId();
        restMockMvc
            .perform(post("/api/user/orders/cancel/" + invalidOrderId).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        assertThat(order.getStatus().getId()).isEqualTo(oldStatusId);
    }
}
