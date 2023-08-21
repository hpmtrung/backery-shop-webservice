package com.bakery.shop.service;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatus;
import com.bakery.shop.service.dto.user.UserOrderWithDetailsDTO;
import com.bakery.shop.service.dto.user.UserOverviewOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserOrderService {
    Page<UserOverviewOrderDTO> getUserOrderOverviews(Account account, Pageable pageable);

    Page<UserOverviewOrderDTO> getUserOrderOverviewsFilterByStatus(Account user, OrderStatus status, Pageable pageable);

    UserOrderWithDetailsDTO getUserOrderWithDetails(long orderId);

    void cancelUserOrder(Order order);
}
