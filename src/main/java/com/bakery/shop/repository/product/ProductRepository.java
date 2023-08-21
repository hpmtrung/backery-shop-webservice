package com.bakery.shop.repository.product;

import com.bakery.shop.domain.product.Product;
import com.bakery.shop.domain.product.ProductCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    Optional<Product> findByName(String name);

    Page<Product> findAllByCategory(ProductCategory category, Pageable pageable);

    Page<Product> findAllByCategoryAndAvailableIsTrue(ProductCategory category, Pageable pageable);
}
