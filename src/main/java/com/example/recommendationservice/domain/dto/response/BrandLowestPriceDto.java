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
public class BrandLowestPriceDto {
    private String brand;
    private List<CategoryPriceDto> category;
    private Integer totalPrice;

    public static BrandLowestPriceDto from(Brand brand, List<Product> products) {
        List<CategoryPriceDto> categoryList = products.stream()
                .map(CategoryPriceDto::from)
                .toList();

        return new BrandLowestPriceDto(
                brand.getName(),
                categoryList,
                products.stream().mapToInt(Product::getPrice).sum()
        );
    }
}
