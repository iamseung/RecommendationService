package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MinPricePerCategoryResponse {

    private List<MinPricePerCategoryItem> items;
    private int totalPrice;

    public static MinPricePerCategoryResponse from(List<Product> products) {
        return new MinPricePerCategoryResponse(
                products.stream().map(MinPricePerCategoryItem::from).toList(),
                products.stream().mapToInt(Product::getPrice).sum());
    }
}
