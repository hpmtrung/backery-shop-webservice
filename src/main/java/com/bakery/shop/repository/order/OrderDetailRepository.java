package com.bakery.shop.repository.order;

import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderDetail;
import com.bakery.shop.domain.order.OrderDetailId;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findAllById_Order(Order order);

    @Query("select sum(quantity) from OrderDetail ")
    long sumQuantity();

    @Query(
        "select sum(od.quantity) from OrderDetail od join Order o on od.id" +
        ".order = o where o.createdAt between :fromInstant and " +
        ":toInstant"
    )
    long sumQuantityByCreatedAtBetween(@Param("fromInstant") Instant fromInstant, @Param("toInstant") Instant toInstant);
}
