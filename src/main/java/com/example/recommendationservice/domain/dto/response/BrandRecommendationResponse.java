package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRecommendationResponse {
    private BrandLowestPriceDto lowestPrice;

    public static BrandRecommendationResponse from(Brand brand, List<Product> products) {
        return new BrandRecommendationResponse(BrandLowestPriceDto.from(brand,products));
    }
}
