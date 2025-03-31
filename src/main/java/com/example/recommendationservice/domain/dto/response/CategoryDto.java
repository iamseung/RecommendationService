package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String category;

    public static CategoryDto from(Category category) {
        return new CategoryDto(category.getId(), category.getName()) ;
    }
}
