package com.example.skinCareApp.Repository;

import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    Optional<Product> findById(Long productId);

    List<Product> findByCategory(Category category);
}

