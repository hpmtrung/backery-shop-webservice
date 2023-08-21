package com.bakery.shop.web.rest;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatus;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.OrderService;
import com.bakery.shop.service.UserOrderService;
import com.bakery.shop.service.dto.user.UserOrderWithDetailsDTO;
import com.bakery.shop.service.dto.user.UserOverviewOrderDTO;
import com.bakery.shop.web.rest.errors.EntityNotFoundException;
import com.bakery.shop.web.rest.errors.OrderAccountNotFoundException;
import com.bakery.shop.web.rest.errors.OrderIdNotFoundException;
import com.bakery.shop.web.rest.errors.OrderInvalidCancelException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserOrdersResource {

    private final AccountService accountService;
    private final OrderRepository orderRepository;
    private final UserOrderService userOrderService;
    private final OrderService orderService;
    private final OrderStatusRepository orderStatusRepository;

    public UserOrdersResource(
        AccountService accountService,
        OrderRepository orderRepository,
        UserOrderService userOrderService,
        OrderService orderService,
        OrderStatusRepository orderStatusRepository
    ) {
        this.accountService = accountService;
        this.orderRepository = orderRepository;
        this.userOrderService = userOrderService;
        this.orderService = orderService;
        this.orderStatusRepository = orderStatusRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserOverviewOrderDTO>> getAllUserOrderOverviews(@PageableDefault Pageable pageable) {
        Account account = accountService.getLoginAccount().orElseThrow();

        Page<UserOverviewOrderDTO> page = userOrderService.getUserOrderOverviews(account, pageable);

        final String productImagePrefix = ResourceUtil.getProductImageURLPrefix();
        page.forEach(userOverviewOrderDTO ->
            userOverviewOrderDTO.getFirstDetail().setImageUrl(productImagePrefix + userOverviewOrderDTO.getFirstDetail().getImageUrl())
        );
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<UserOverviewOrderDTO>> getUserOrderOverviewsFilterByStatus(
        @PathVariable("statusId") Integer statusId,
        @PageableDefault Pageable pageable
    ) {
        Account account = accountService.getLoginAccount().orElseThrow();
        OrderStatus filterStatus = orderStatusRepository
            .findById(statusId)
            .orElseThrow(() -> new EntityNotFoundException("Filter status id is not found", "order", "notFoundStatusId"));
        Page<UserOverviewOrderDTO> page = userOrderService.getUserOrderOverviewsFilterByStatus(account, filterStatus, pageable);
        final String productImagePrefix = ResourceUtil.getProductImageURLPrefix();
        page.forEach(userOverviewOrderDTO ->
            userOverviewOrderDTO.getFirstDetail().setImageUrl(productImagePrefix + userOverviewOrderDTO.getFirstDetail().getImageUrl())
        );
        return new ResponseEntity<>(page.getContent(), ResourceUtil.getHeaderFromPage(page), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public UserOrderWithDetailsDTO getUserOrderWithDetails(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotFoundException::new);

        Account loginUser = accountService.getLoginAccount().orElseThrow();
        if (!orderRepository.existsByOrderedByAndId(loginUser, orderId)) {
            throw new OrderAccountNotFoundException();
        }
        final String productImagePrefix = ResourceUtil.getProductImageURLPrefix();
        UserOrderWithDetailsDTO userOrderWithDetailsDTO = userOrderService.getUserOrderWithDetails(orderId);
        userOrderWithDetailsDTO
            .getDetails()
            .forEach(orderDetailDTO -> orderDetailDTO.setImageUrl(productImagePrefix + orderDetailDTO.getImageUrl()));
        return userOrderWithDetailsDTO;
    }

    @PutMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderIdNotFoundException::new);
        Account loginUser = accountService.getLoginAccount().orElseThrow();
        if (!orderRepository.existsByOrderedByAndId(loginUser, orderId)) {
            throw new OrderAccountNotFoundException();
        }

        if (!orderService.canCancelOrder(order)) {
            throw new OrderInvalidCancelException();
        }
        userOrderService.cancelUserOrder(order);
    }

    public static class UserOrderResourceException extends RuntimeException {

        private static final long serialVersionUID = -6923097471237685421L;

        public UserOrderResourceException() {}
    }
}
