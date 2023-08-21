package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.ProductType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    Optional<ProductType> findByName(String name);
}
