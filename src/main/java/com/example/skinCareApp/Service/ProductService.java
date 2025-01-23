package com.example.skinCareApp.Service;

import com.example.skinCareApp.Entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product name, Long categoryId);
    List<Product> getProductsByCategory(Long categoryId);
    Product getProductById(Long id);
}

