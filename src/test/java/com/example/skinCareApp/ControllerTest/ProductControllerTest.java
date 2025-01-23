package com.example.skinCareApp.ControllerTest;

import com.example.skinCareApp.Controller.ProductController;
import com.example.skinCareApp.Entity.Product;
import com.example.skinCareApp.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Moisturizer");

        when(productService.createProduct(any(Product.class), eq(1L))).thenReturn(product);

        mockMvc.perform(post("/products/create")
                        .param("categoryId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Moisturizer\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Moisturizer"));

        verify(productService, times(1)).createProduct(any(Product.class), eq(1L));
    }

    @Test
    void testGetProductsByCategory() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Cleanser");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Serum");

        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.getProductsByCategory(1L)).thenReturn(productList);

        mockMvc.perform(get("/products/by-category")
                        .param("categoryId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Cleanser"))
                .andExpect(jsonPath("$[1].name").value("Serum"));

        verify(productService, times(1)).getProductsByCategory(1L);
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Toner");

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/by-ID")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Toner"));

        verify(productService, times(1)).getProductById(1L);
    }
}
