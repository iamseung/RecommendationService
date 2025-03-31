package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.entity.Product;

import java.util.List;

public interface MinPriceCategorySelectionStrategy {
    List<Product> apply(List<Product> products);
}

