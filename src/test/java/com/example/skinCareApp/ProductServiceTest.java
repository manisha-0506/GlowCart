package com.example.skinCareApp;

import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Repository.CategoryRepository;
import com.example.skinCareApp.Repository.ProductRepository;
import com.example.skinCareApp.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Category category;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Skincare");

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Face Wash");
        product1.setCategory(category);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Moisturizer");
        product2.setCategory(category);
    }

    @Test
    void testGetProductsByCategory_WhenCategoryExists() {
        // Mock behavior
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category)).thenReturn(Arrays.asList(product1, product2));

        // Call method
        List<Product> products = productService.getProductsByCategory(1L);

        // Assertions
        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Face Wash", products.get(0).getName());
        assertEquals("Moisturizer", products.get(1).getName());

        // Verify interactions
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void testGetProductsByCategory_WhenCategoryDoesNotExist() {
        // Mock behavior
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        // Call method and assert exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductsByCategory(2L);
        });

        assertEquals("Category with ID 2 not found", exception.getMessage());

        // Verify interactions
        verify(categoryRepository, times(1)).findById(2L);
        verify(productRepository, never()).findByCategory(any());
    }
}
