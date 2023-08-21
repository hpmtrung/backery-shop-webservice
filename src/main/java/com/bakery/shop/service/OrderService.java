package com.bakery.shop.service;

import com.bakery.shop.domain.order.Order;
import com.bakery.shop.service.dto.admin.order.AdminOrderWithDetailsDTO;
import com.bakery.shop.service.dto.admin.order.AdminOverviewOrderDTO;
import com.bakery.shop.service.dto.user.UserOrderWithDetailsDTO;
import com.bakery.shop.service.dto.user.UserOverviewOrderDTO;

public interface OrderService {
    boolean canCancelOrder(Order order);

    UserOverviewOrderDTO getUserOrderOverviewDTOFromOrder(Order order);

    UserOrderWithDetailsDTO getUserOrderWithDetailsDTOFromOrder(Order order);

    AdminOverviewOrderDTO getAdminOverviewOrderDTOFromOrder(Order order);

    AdminOrderWithDetailsDTO getAdminOrderWithDetailsDTOFromOrder(Order order);
}
