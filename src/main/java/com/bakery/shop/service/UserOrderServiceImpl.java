package com.bakery.shop.service;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatus;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.service.dto.user.UserOrderWithDetailsDTO;
import com.bakery.shop.service.dto.user.UserOverviewOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserOrderServiceImpl implements UserOrderService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public UserOrderServiceImpl(OrderService orderService, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public Page<UserOverviewOrderDTO> getUserOrderOverviews(Account account, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByOrderedByOrderByCreatedAtDesc(account, pageable);
        return orders.map(orderService::getUserOrderOverviewDTOFromOrder);
    }

    @Override
    public Page<UserOverviewOrderDTO> getUserOrderOverviewsFilterByStatus(Account account, OrderStatus status, Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByOrderedByAndStatusOrderByCreatedAtDesc(account, status, pageable);
        return orders.map(orderService::getUserOrderOverviewDTOFromOrder);
    }

    @Override
    public UserOrderWithDetailsDTO getUserOrderWithDetails(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return orderService.getUserOrderWithDetailsDTOFromOrder(order);
    }

    @Override
    @Transactional(rollbackFor = { RuntimeException.class, Throwable.class })
    public void cancelUserOrder(Order order) {
        order.setStatus(orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow());
        orderRepository.save(order);
    }
}
