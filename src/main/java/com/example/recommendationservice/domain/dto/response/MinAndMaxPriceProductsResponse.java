package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MinAndMaxPriceProductsResponse {
    String category;
    BrandWithPriceDto lowestPrice;
    BrandWithPriceDto highestPrice;

    public static MinAndMaxPriceProductsResponse from(Category category, MinMaxProductDto products) {
        return new MinAndMaxPriceProductsResponse(
                category.getName(),
                BrandWithPriceDto.from(products.getMinProduct()),
                BrandWithPriceDto.from(products.getMaxProduct())
        );
    }
}
