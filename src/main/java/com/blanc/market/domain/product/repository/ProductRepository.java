package com.blanc.market.domain.product.repository;

import com.blanc.market.domain.product.entity.Category;
import com.blanc.market.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String keyword);
    Page<Product> findByNameContaining(String keyword, Pageable pageable);
    Page<Product> findByCategory(Category category, Pageable pageable);

}
