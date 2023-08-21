package com.bakery.shop.service;

import com.bakery.shop.service.dto.admin.order.AdminOrderAfterUpdateStatusDTO;
import com.bakery.shop.service.dto.admin.order.AdminOrderWithDetailsDTO;
import com.bakery.shop.service.dto.admin.order.AdminOverviewOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {
    Page<AdminOverviewOrderDTO> getAllManagedOverviewOrders(Pageable pageable);

    Page<AdminOverviewOrderDTO> getOverviewOrdersOfAccount(long accountId, Pageable pageable);

    AdminOrderWithDetailsDTO getManagedOrderWithDetails(long orderId);

    AdminOrderAfterUpdateStatusDTO updateOrderStatusToUpperState(long orderId);

    AdminOrderAfterUpdateStatusDTO cancelManagedOrder(long orderId);
}
