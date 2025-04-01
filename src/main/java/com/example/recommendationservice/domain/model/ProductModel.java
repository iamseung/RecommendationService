package com.example.recommendationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductModel {
    private Long brandId;
    private Long categoryId;
    private Integer price;

    public static ProductModel of(Long brandId, Long categoryId, Integer price) {
        return new ProductModel(brandId, categoryId, price);
    }
}
