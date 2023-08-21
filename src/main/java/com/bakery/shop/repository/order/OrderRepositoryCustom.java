package com.bakery.shop.repository.order;

import com.bakery.shop.domain.order.Order;
import com.bakery.shop.service.dto.admin.order.AdminStatisticOrderDTO;
import java.util.List;

/** Custom Spring Data JPA repository for the {@link Order} entity. */
public interface OrderRepositoryCustom {
    List<AdminStatisticOrderDTO> findTopRecentAtNumber(int number);
}
