package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.response.ApiSuccessResponse;
import com.example.recommendationservice.domain.dto.response.CategoryResponse;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카테고리 API", description = "프론트 데이터를 위한 API")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<ApiSuccessResponse<CategoryResponse>> getAllCategory() {
        List<Category> allCategories = categoryService.findAllCategories();
        return ResponseEntity.ok(ApiSuccessResponse.ok(CategoryResponse.from(allCategories)));
    }
}
