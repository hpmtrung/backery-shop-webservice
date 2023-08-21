package com.bakery.shop.repository.order;

import com.bakery.shop.domain.order.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    @Override
    @Query("select s from OrderStatus s order by s.id")
    List<OrderStatus> findAll();
}
