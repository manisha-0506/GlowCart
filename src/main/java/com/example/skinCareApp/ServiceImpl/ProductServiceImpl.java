package com.example.skinCareApp.ServiceImpl;

import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Exception.CategoryNotFoundException;
import com.example.skinCareApp.Exception.NoProductsFoundException;
import com.example.skinCareApp.Exception.ProductNotFoundException;
import com.example.skinCareApp.Repository.CategoryRepository;
import com.example.skinCareApp.Repository.ProductRepository;
import com.example.skinCareApp.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product products, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        products.setCategory(category);

        return productRepository.save(products);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        // Validate category exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + categoryId + " not found"));

        // Fetch products
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new NoProductsFoundException("No products found for category ID " + categoryId);
        }

        return products;
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }
}
