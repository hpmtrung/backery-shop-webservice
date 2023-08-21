package com.bakery.shop.repository.product;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductVariantRepositoryImpl implements ProductVariantRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public boolean isSold(Integer variantId) {
        boolean isSold;
        isSold =
            em
                .unwrap(Session.class)
                .createQuery(
                    "SELECT 1 FROM ProductVariant P WHERE EXISTS " + "(SELECT 1 FROM OrderDetail O WHERE O.id.variant.id = :variantId)"
                )
                .setParameter("variantId", variantId)
                .uniqueResult() !=
            null;
        return isSold;
    }
    // @Override
    // public long countAllAvailableVariant() {
    //     long count = (Long) em.unwrap(Session.class).createQuery(
    // 			"SELECT COUNT(*) FROM Product p JOIN p.variants v "
    // 			+ "WHERE p.available = true AND v.available = true"
    // 			).uniqueResult();
    // 	return count;
    // }
}
