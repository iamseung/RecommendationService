package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.dto.response.BrandRecommendationResponse;
import com.example.recommendationservice.domain.dto.response.MinAndMaxPriceProductsResponse;
import com.example.recommendationservice.domain.dto.response.MinPricePerCategoryResponse;

public interface RecommendationService {
    MinPricePerCategoryResponse getMinPricePerCategory();
    BrandRecommendationResponse getBrandWithLowestTotalPrice();
    MinAndMaxPriceProductsResponse getMinAndMaxPriceProducts(String categoryName);
}
