package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPriceDto {
    private String category;
    private Integer price;

    public static CategoryPriceDto from(Product product) {
        return new CategoryPriceDto(
                product.getCategory().getName(),
                product.getPrice()
        );
    }
}
