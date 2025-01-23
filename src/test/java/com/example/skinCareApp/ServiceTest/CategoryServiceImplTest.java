package com.example.skinCareApp.ServiceTest;

import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Repository.CategoryRepository;
import com.example.skinCareApp.ServiceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Skincare");
    }

    @Test
    void testCreateCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.createCategory("Skincare");

        assertNotNull(createdCategory);
        assertEquals("Skincare", createdCategory.getName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testGetCategoryByName_Success() {
        when(categoryRepository.findByName("Skincare")).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.getCategoryByName("Skincare");

        assertNotNull(foundCategory);
        assertEquals("Skincare", foundCategory.getName());
    }

    @Test
    void testGetCategoryByName_NotFound() {
        when(categoryRepository.findByName("Unknown")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.getCategoryByName("Unknown"));

        assertEquals("Category not found", exception.getMessage());
    }
}
