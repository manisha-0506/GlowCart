package com.example.skinCareApp.ControllerTest;

import com.example.skinCareApp.Controller.CategoryController;
import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Skincare");

        when(categoryService.createCategory("Skincare")).thenReturn(category);

        mockMvc.perform(post("/categories/create")
                        .param("name", "Skincare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Skincare"));

        verify(categoryService, times(1)).createCategory("Skincare");
    }

    @Test
    void testGetAllCategories() throws Exception {
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Skincare");

        Category cat2 = new Category();
        cat2.setId(2L);
        cat2.setName("Haircare");

        List<Category> categoryList = Arrays.asList(cat1, cat2);

        when(categoryService.getAllCategories()).thenReturn(categoryList);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Skincare"))
                .andExpect(jsonPath("$[1].name").value("Haircare"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryByName() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Skincare");

        when(categoryService.getCategoryByName("Skincare")).thenReturn(category);

        mockMvc.perform(get("/categories/by-name")
                        .param("name", "Skincare"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Skincare"));

        verify(categoryService, times(1)).getCategoryByName("Skincare");
    }
}
