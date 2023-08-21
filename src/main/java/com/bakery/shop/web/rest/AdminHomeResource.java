package com.bakery.shop.web.rest;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.order.OrderDetailRepository;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.web.rest.vm.AdminHomeStatisticVM;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminHomeResource {

    public static final int TOP_RECENT_ORDER_NUM = 5;

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductVariantRepository variantRepository;

    public AdminHomeResource(
        OrderRepository orderRepository,
        OrderDetailRepository orderDetailRepository,
        OrderStatusRepository orderStatusRepository,
        ProductVariantRepository variantRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.variantRepository = variantRepository;
    }

    @GetMapping("/home")
    @Transactional(readOnly = true)
    public AdminHomeStatisticVM getHomeStatisticInfo() {
        final Instant startTodayInstant = LocalDate.now().atTime(LocalTime.MIN).toInstant(ZoneOffset.UTC);
        final Instant endTodayInstant = LocalDate.now().atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC);
        return new AdminHomeStatisticVM()
            .setTotalOrdersNum(orderRepository.count())
            .setTodayOrdersNum(orderRepository.countByCreatedAtBetween(startTodayInstant, endTodayInstant))
            .setTodayProcessingOrdersNum(
                orderRepository.countByCreatedAtBetweenAndStatus(
                    startTodayInstant,
                    endTodayInstant,
                    orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow()
                )
            )
            .setTodayCancelOrdersNum(
                orderRepository.countByCreatedAtBetweenAndStatus(
                    startTodayInstant,
                    endTodayInstant,
                    orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow()
                )
            )
            .setTodayDispatchOrdersNum(
                orderRepository.countByCreatedAtBetweenAndStatus(
                    startTodayInstant,
                    endTodayInstant,
                    orderStatusRepository.findById(OrderStatusConstants.DISPATCH.getId()).orElseThrow()
                )
            )
            .setTodayShippedOrdersNum(
                orderRepository.countByCreatedAtBetweenAndStatus(
                    startTodayInstant,
                    endTodayInstant,
                    orderStatusRepository.findById(OrderStatusConstants.SHIPPED.getId()).orElseThrow()
                )
            )
            .setTotalAvailableProductVariantsNum(variantRepository.countAllByAvailableIsTrue())
            .setTotalSoldProductVariantsNum(orderDetailRepository.sumQuantity())
            // .setTodaySoldProductVariantsNum(orderDetailRepository.sumQuantityByCreatedAtBetween(startTodayInstant, endTodayInstant))
            .setTopRecentOrders(orderRepository.findTopRecentAtNumber(TOP_RECENT_ORDER_NUM));
    }
}
