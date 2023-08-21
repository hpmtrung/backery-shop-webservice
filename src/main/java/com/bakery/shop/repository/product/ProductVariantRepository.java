package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductType;
import com.bakery.shop.domain.product.ProductVariant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer>, ProductVariantRepositoryCustom {
    List<ProductVariant> findByProduct(Product product);
    Optional<ProductVariant> findByProductAndProductType(Product product, ProductType productType);
    long countAllByAvailableIsTrue();
}
