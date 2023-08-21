package com.bakery.shop.web.rest.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.security.AuthoritiesConstants;
import com.bakery.shop.web.rest.AdminOrdersResource;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** Integration tests for the {@link AdminOrdersResource} REST controller. */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
class AdminOrdersResourceIT {

    private static final long TEST_ORDER_ID_HAS_PROCESSING_STATUS = 18L;
    private static final long TEST_ORDER_ID_HAS_DISPATCH_STATUS = 16L;
    private static final long TEST_ORDER_ID_HAS_SHIPPED_STATUS = 17L;
    private static final long TEST_ORDER_ID_HAS_CANCEL_STATUS = 33L;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private MockMvc restMockMVC;

    @Test
    @Transactional
    public void testGetAllOrderStatuses() throws Exception {
        restMockMVC
            .perform(get("/api/admin/orders/status").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").isNotEmpty())
            .andExpect(jsonPath("$.[*].name").isNotEmpty());
    }

    @Test
    @Transactional
    public void testGetAllOrders() throws Exception {
        restMockMVC
            .perform(get("/api/admin/orders").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].accountId").isNotEmpty())
            .andExpect(jsonPath("$.[*].fullName").isNotEmpty())
            .andExpect(jsonPath("$.[*].paidByCash").isNotEmpty())
            .andExpect(jsonPath("$.[*].address").isNotEmpty())
            .andExpect(jsonPath("$.[*].note").exists());
    }

    @Test
    @Transactional
    public void testGetValidOrderDetails() throws Exception {
        final Order validOrder = orderRepository.findAll().iterator().next();

        restMockMVC
            .perform(get("/api/admin/orders/" + validOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(validOrder.getId()))
            // .andExpect(jsonPath("$.createdAt").value(validOrder.getCreatedAt()))
            .andExpect(jsonPath("$.statusId").value(validOrder.getStatus().getId()))
            .andExpect(jsonPath("$.statusName").value(validOrder.getStatus().getName()))
            .andExpect(jsonPath("$.total").value(validOrder.getTotal()))
            .andExpect(jsonPath("$.canCancel").value(validOrder.getStatus().getId().equals(OrderStatusConstants.PROCESSING.getId())))
            .andExpect(jsonPath("$.accountId").value(validOrder.getOrderedBy().getId()))
            .andExpect(
                jsonPath("$.fullName").value(validOrder.getOrderedBy().getLastName() + " " + validOrder.getOrderedBy().getFirstName())
            )
            .andExpect(jsonPath("$.paidByCash").value(validOrder.isPaidByCash()))
            .andExpect(jsonPath("$.address").value(validOrder.getAddress()))
            .andExpect(jsonPath("$.note").value(validOrder.getNote()))
            .andExpect(jsonPath("$.details").isNotEmpty())
            .andExpect(jsonPath("$.details").isArray())
            .andExpect(jsonPath("$.details").value(hasSize(validOrder.getOrderDetails().size())));
    }

    @Test
    @Transactional
    public void testGetOrderDetailsWithNotExistingId() throws Exception {
        restMockMVC.perform(get("/api/admin/orders/" + 0).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testUpdateValidOrderStatus() throws Exception {
        final Order validOrder = orderRepository.findById(TEST_ORDER_ID_HAS_PROCESSING_STATUS).orElseThrow();
        final int beforeUpdateOrderStatusId = validOrder.getStatus().getId();
        restMockMVC
            .perform(put("/api/admin/orders/" + validOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orderId").value(validOrder.getId()))
            .andExpect(jsonPath("$.updatedStatusId").value(beforeUpdateOrderStatusId + 1))
            .andExpect(
                jsonPath("$.updatedStatusName").value(orderStatusRepository.findById(beforeUpdateOrderStatusId + 1).orElseThrow().getName())
            )
            .andExpect(jsonPath("$.canCancel").value(false));
        assertThat(validOrder.getStatus().getId()).isEqualTo(beforeUpdateOrderStatusId + 1);
    }

    @Test
    @Transactional
    public void testUpdateOrderStatusWithNotExistingOrderId() throws Exception {
        restMockMVC.perform(put("/api/admin/orders/" + 0).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testUpdateInvalidOrderStatus() throws Exception {
        restMockMVC
            .perform(put("/api/admin/orders/" + TEST_ORDER_ID_HAS_SHIPPED_STATUS).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        restMockMVC
            .perform(put("/api/admin/orders/" + TEST_ORDER_ID_HAS_CANCEL_STATUS).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testCancelValidOrder() throws Exception {
        restMockMVC
            .perform(post("/api/admin/orders/cancel/" + TEST_ORDER_ID_HAS_PROCESSING_STATUS).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        restMockMVC
            .perform(post("/api/admin/orders/cancel/" + TEST_ORDER_ID_HAS_DISPATCH_STATUS).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        restMockMVC
            .perform(post("/api/admin/orders/cancel/" + TEST_ORDER_ID_HAS_SHIPPED_STATUS).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.orderId").value(TEST_ORDER_ID_HAS_SHIPPED_STATUS))
            .andExpect(jsonPath("$.updatedStatusId").value(OrderStatusConstants.CANCEL.getId()))
            .andExpect(
                jsonPath("$.updatedStatusName")
                    .value(orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow().getName())
            )
            .andExpect(jsonPath("$.canCancel").value(false));
    }

    @Test
    @Transactional
    public void testCancelOrderWithNotExistingOrderId() throws Exception {
        restMockMVC.perform(post("/api/admin/orders/cancel/" + 0).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    private void assertPersistedOrders(Consumer<Iterable<Order>> orderAssert) {
        orderAssert.accept(orderRepository.findAll());
    }
}
