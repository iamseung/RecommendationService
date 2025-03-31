package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.MinMaxProductDto;
import com.example.recommendationservice.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class BrandSortedMinMaxStrategy implements MinAndMaxCategorySelectionStrategy {

    @Override
    public MinMaxProductDto apply(List<Product> products) {
        List<Product> sortedProducts = sortProducts(products);

        return new MinMaxProductDto(sortedProducts.get(0), sortedProducts.get(sortedProducts.size() - 1));
    }

    private List<Product> sortProducts(List<Product> products) {
        return products.stream().sorted(
                Comparator.comparingInt(Product::getPrice)
                .thenComparing(p -> p.getBrand().getId())
        ).toList();
    }
}
