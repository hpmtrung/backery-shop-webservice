package com.bakery.shop.repository.order;

import com.bakery.shop.service.dto.admin.order.AdminStatisticOrderDTO;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final EntityManager em;

    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<AdminStatisticOrderDTO> findTopRecentAtNumber(int number) {
        return em
            .unwrap(Session.class)
            .createQuery(
                "select new com.bakery.shop.service.dto.admin.order.AdminStatisticOrderDTO(" +
                "o.id, a.imageUrl, concat(a.lastName, ' ', a.firstName), o.createdAt," +
                " o.total, o.paidByCash, s.id, s.name, o.note" +
                ") from Order o join Account a on o.orderedBy = a join OrderStatus s on o.status = s" +
                " order by o.createdAt desc",
                AdminStatisticOrderDTO.class
            )
            .setMaxResults(number)
            .list();
    }
}
