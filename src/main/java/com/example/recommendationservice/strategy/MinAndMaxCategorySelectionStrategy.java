package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.MinMaxProductDto;
import com.example.recommendationservice.domain.entity.Product;

import java.util.List;

public interface MinAndMaxCategorySelectionStrategy {

      MinMaxProductDto apply(List<Product> products);
}
