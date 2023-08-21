package com.bakery.shop.service;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.service.dto.admin.order.AdminOrderAfterUpdateStatusDTO;
import com.bakery.shop.service.dto.admin.order.AdminOrderWithDetailsDTO;
import com.bakery.shop.service.dto.admin.order.AdminOverviewOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AccountRepository accountRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    public AdminOrderServiceImpl(
        AccountRepository accountRepository,
        OrderService orderService,
        OrderRepository orderRepository,
        OrderStatusRepository orderStatusRepository
    ) {
        this.accountRepository = accountRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminOverviewOrderDTO> getAllManagedOverviewOrders(Pageable pageable) {
        return orderRepository.findAllByOrderByCreatedAtDesc(pageable).map(orderService::getAdminOverviewOrderDTOFromOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminOverviewOrderDTO> getOverviewOrdersOfAccount(long accountId, Pageable pageable) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return orderRepository
            .findAllByOrderedByOrderByCreatedAtDesc(account, pageable)
            .map(orderService::getAdminOverviewOrderDTOFromOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminOrderWithDetailsDTO getManagedOrderWithDetails(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        AdminOrderWithDetailsDTO adminOrderWithDetailsDTO = orderService.getAdminOrderWithDetailsDTOFromOrder(order);
        return adminOrderWithDetailsDTO;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Throwable.class)
    public AdminOrderAfterUpdateStatusDTO updateOrderStatusToUpperState(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(orderStatusRepository.findById(order.getStatus().getId() + 1).orElseThrow());
        orderRepository.save(order);
        return getAdminOrderAfterUpdateStatusDTOFromOrder(order);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Throwable.class)
    public AdminOrderAfterUpdateStatusDTO cancelManagedOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(orderStatusRepository.findById(OrderStatusConstants.CANCEL.getId()).orElseThrow());
        orderRepository.save(order);
        return getAdminOrderAfterUpdateStatusDTOFromOrder(order);
    }

    private AdminOrderAfterUpdateStatusDTO getAdminOrderAfterUpdateStatusDTOFromOrder(Order order) {
        return new AdminOrderAfterUpdateStatusDTO()
            .setOrderId(order.getId())
            .setUpdatedStatusId(order.getStatus().getId())
            .setUpdatedStatusName(order.getStatus().getName())
            .setCanCancel(orderService.canCancelOrder(order));
    }
}
