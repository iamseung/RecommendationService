package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandWithPriceDto {
    String brand;
    Integer price;

    public static BrandWithPriceDto from(Product product) {
        return new BrandWithPriceDto(product.getBrand().getName(), product.getPrice());
    }
}
