package com.bakery.shop.repository.order;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderStatus;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/** Spring Data JPA repository for the {@link Order} entity. */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, OrderRepositoryCustom {
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Order> findAllByOrderedByOrderByCreatedAtDesc(Account account, Pageable pageable);

    Page<Order> findAllByOrderedByAndStatusOrderByCreatedAtDesc(Account account, OrderStatus status, Pageable pageable);

    boolean existsByOrderedByAndId(Account account, long orderId);

    long countByCreatedAtBetween(Instant fromInstant, Instant toInstant);

    long countByCreatedAtBetweenAndStatus(Instant fromInstant, Instant toInstant, OrderStatus status);
}
