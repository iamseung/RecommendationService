package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinPricePerCategoryItem {

    private String category;
    private String brand;
    private Integer price;

    public static MinPricePerCategoryItem of(String category, String brand, Integer price) {
        return new MinPricePerCategoryItem(category, brand, price);
    }

    public static MinPricePerCategoryItem from(Product product) {
        return of(
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getPrice()
        );
    }
}
