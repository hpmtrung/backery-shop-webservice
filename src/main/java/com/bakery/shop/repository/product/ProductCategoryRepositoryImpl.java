package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.ProductCategory;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepositoryCustom {

    private final EntityManager em;

    public ProductCategoryRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int countNumberOfAvailableProducts(ProductCategory category) {
        Integer numberOfAvailableProducts = (Integer) em
            .unwrap(Session.class)
            .createSQLQuery("EXEC countAvailableProductInCategory :categoryId")
            .setParameter("categoryId", category.getId())
            .uniqueResult();
        return numberOfAvailableProducts;
    }
}
