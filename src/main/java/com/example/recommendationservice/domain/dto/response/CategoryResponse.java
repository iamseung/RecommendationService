package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private List<CategoryDto> categories;

    public static CategoryResponse from(List<Category> categories) {
        return new CategoryResponse(
                categories
                        .stream()
                        .sorted(Comparator.comparingLong(Category::getId))
                        .map(CategoryDto::from)
                        .toList()
        );
    }
}
