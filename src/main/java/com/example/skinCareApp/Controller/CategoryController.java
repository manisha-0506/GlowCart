package com.example.skinCareApp.Controller;

import com.example.skinCareApp.Entity.Category;
import com.example.skinCareApp.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public Category createCategory(@RequestParam String name) {
        return categoryService.createCategory(name);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/by-name")
    public Category getCategoryByName(@RequestParam String name) {
        return categoryService.getCategoryByName(name);
    }
}

