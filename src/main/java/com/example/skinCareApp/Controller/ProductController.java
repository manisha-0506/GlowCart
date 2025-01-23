package com.example.skinCareApp.Controller;

import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public Product createProduct( @RequestParam Long categoryId, @RequestBody Product product) {
        return productService.createProduct(product, categoryId);
    }

    @GetMapping("/by-category")
    public List<Product> getProductsByCategory(@RequestParam Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
    @GetMapping("/by-ID")
    public Product getProductsById(@RequestParam Long id) {
        return productService.getProductById(id);
    }


}

