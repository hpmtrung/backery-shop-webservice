package com.bakery.shop.web.rest;

import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.service.AdminOrderService;
import com.bakery.shop.service.dto.admin.order.AdminOrderAfterUpdateStatusDTO;
import com.bakery.shop.service.dto.admin.order.AdminOrderWithDetailsDTO;
import com.bakery.shop.service.dto.admin.order.AdminOverviewOrderDTO;
import com.bakery.shop.web.rest.errors.AccountIdNotFoundException;
import com.bakery.shop.web.rest.errors.ErrorEntityValidationException;
import com.bakery.shop.web.rest.errors.OrderIdNotFoundException;
import com.bakery.shop.web.rest.errors.OrderInvalidCancelException;
import java.util.List;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminOrdersResource {

    private final AccountRepository accountRepository;
    private final AdminOrderService adminOrderService;
    private final OrderRepository orderRepository;

    public AdminOrdersResource(AccountRepository accountRepository, AdminOrderService adminOrderService, OrderRepository orderRepository) {
        this.accountRepository = accountRepository;
        this.adminOrderService = adminOrderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<List<AdminOverviewOrderDTO>> getAllOverviewOrder(@ParameterObject @PageableDefault Pageable pageable) {
        final Page<AdminOverviewOrderDTO> page = adminOrderService.getAllManagedOverviewOrders(pageable);
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<AdminOverviewOrderDTO>> getAllOverviewOrderOfAccount(
        @PathVariable Long accountId,
        @ParameterObject @PageableDefault Pageable pageable
    ) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new AccountIdNotFoundException();
        }
        final Page<AdminOverviewOrderDTO> page = adminOrderService.getOverviewOrdersOfAccount(accountId, pageable);
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public AdminOrderWithDetailsDTO getManagedOrderWithDetail(@PathVariable("orderId") Long orderId) {
        if (orderRepository.findById(orderId).isEmpty()) {
            throw new OrderIdNotFoundException();
        }
        return adminOrderService.getManagedOrderWithDetails(orderId);
    }

    @PutMapping("/{orderId}/status/update")
    public AdminOrderAfterUpdateStatusDTO setOrderStatusToUpperState(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotFoundException::new);

        final int currentOrderStatusId = order.getStatus().getId();

        if (List.of(OrderStatusConstants.SHIPPED.getId(), OrderStatusConstants.CANCEL.getId()).contains(currentOrderStatusId)) {
            throw new ErrorEntityValidationException(
                "Current order status is not allowed to set to upper state",
                "order",
                "invalidUpdateStatus"
            );
        }

        return adminOrderService.updateOrderStatusToUpperState(orderId);
    }

    @PutMapping("/{orderId}/cancel")
    public AdminOrderAfterUpdateStatusDTO cancelOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotFoundException::new);

        if (!order.getStatus().getId().equals(OrderStatusConstants.PROCESSING.getId())) {
            throw new OrderInvalidCancelException();
        }

        return adminOrderService.cancelManagedOrder(orderId);
    }
}
