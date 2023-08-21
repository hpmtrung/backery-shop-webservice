package com.bakery.shop.web.rest.admin;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bakery.shop.IntegrationTest;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.order.OrderDetailRepository;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.security.AuthoritiesConstants;
import com.bakery.shop.service.dto.admin.order.AdminStatisticOrderDTO;
import com.bakery.shop.web.rest.AdminHomeResource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/** Integration tests for the {@link AdminHomeResource} REST controller. */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
public class AdminHomeResourceIT {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ProductVariantRepository variantRepository;

    @Autowired
    private MockMvc restMockMvc;

    @Test
    public void testGetHomeStatisticInfo() throws Exception {
        final Instant startTodayInstant = LocalDate.now().atTime(LocalTime.MIN).toInstant(ZoneOffset.UTC);
        final Instant endTodayInstant = LocalDate.now().atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
        System.err.println("startTodayInstant: '" + startTodayInstant + "', " + "endTodayInstant: '" + endTodayInstant + "'");

        final long totalOrdersNum = orderRepository.count();
        System.err.println("totalOrdersNum: " + totalOrdersNum);

        final long todayOrdersNum = orderRepository.countByCreatedAtBetween(startTodayInstant, endTodayInstant);
        System.err.println("todayOrdersNum: " + todayOrdersNum);

        final long todayProcessingOrdersNum = orderRepository.countByCreatedAtBetweenAndStatus(
            startTodayInstant,
            endTodayInstant,
            orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow()
        );
        System.err.println("todayProcessingOrdersNum: " + todayProcessingOrdersNum);

        final long todayCancelOrdersNum = orderRepository.countByCreatedAtBetweenAndStatus(
            startTodayInstant,
            endTodayInstant,
            orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow()
        );
        System.err.println("todayCancelOrdersNum: " + todayCancelOrdersNum);

        final long todayDispatchOrdersNum = orderRepository.countByCreatedAtBetweenAndStatus(
            startTodayInstant,
            endTodayInstant,
            orderStatusRepository.findById(OrderStatusConstants.DISPATCH.getId()).orElseThrow()
        );
        System.err.println("todayDispatchOrdersNum: " + todayDispatchOrdersNum);

        final long todayShippedOrdersNum = orderRepository.countByCreatedAtBetweenAndStatus(
            startTodayInstant,
            endTodayInstant,
            orderStatusRepository.findById(OrderStatusConstants.SHIPPED.getId()).orElseThrow()
        );
        System.err.println("todayShippedOrdersNum: " + todayShippedOrdersNum);

        final long totalAvailableProductVariantsNum = variantRepository.countAllByAvailableIsTrue();
        System.err.println("totalAvailableProductVariantsNum: " + totalAvailableProductVariantsNum);

        final long totalSoldProductVariantsNum = orderDetailRepository.sumQuantity();
        System.err.println("totalSoldProductVariantsNum: " + totalSoldProductVariantsNum);

        final long todaySoldProductVariantsNum = orderDetailRepository.sumQuantityByCreatedAtBetween(startTodayInstant, endTodayInstant);
        System.err.println("todaySoldProductVariantsNum: " + todaySoldProductVariantsNum);

        final List<AdminStatisticOrderDTO> topRecentOrders = orderRepository.findTopRecentAtNumber(AdminHomeResource.TOP_RECENT_ORDER_NUM);
        System.err.println("topRecentOrders:\n\t+ size: " + topRecentOrders.size());
        for (int idx = 0; idx < topRecentOrders.size(); idx++) {
            System.err.println("\t+ [" + idx + "]: " + topRecentOrders.get(idx));
        }

        restMockMvc
            .perform(get("/api/admin/home").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalOrdersNum").value(totalOrdersNum))
            .andExpect(jsonPath("$.todayOrdersNum").value(todayOrdersNum))
            .andExpect(jsonPath("$.todayProcessingOrdersNum").value(todayProcessingOrdersNum))
            .andExpect(jsonPath("$.todayCancelOrdersNum").value(todayCancelOrdersNum))
            .andExpect(jsonPath("$.todayDispatchOrdersNum").value(todayDispatchOrdersNum))
            .andExpect(jsonPath("$.todayShippedOrdersNum").value(todayShippedOrdersNum))
            .andExpect(jsonPath("$.totalAvailableProductVariantsNum").value(totalAvailableProductVariantsNum))
            .andExpect(jsonPath("$.totalSoldProductVariantsNum").value(totalSoldProductVariantsNum))
            .andExpect(jsonPath("$.todaySoldProductVariantsNum").value(todaySoldProductVariantsNum))
            .andExpect(jsonPath("$.topRecentOrders").isNotEmpty())
            .andExpect(jsonPath("$.topRecentOrders").isArray())
            .andExpect(jsonPath("$.topRecentOrders").value(hasSize(topRecentOrders.size())));
    }
}
