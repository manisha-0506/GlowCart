package com.example.skinCareApp.Service;

import com.example.skinCareApp.Entity.Category;

import java.util.List;

public interface CategoryService {

        Category createCategory(String name);
        List<Category> getAllCategories();
        Category getCategoryByName(String name);

}


