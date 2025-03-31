package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findByName(String categoryName);
    Category findById(Long categoryId);
}
