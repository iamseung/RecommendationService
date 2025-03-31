package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FindFirstMinPriceProductStrategy implements MinPriceCategorySelectionStrategy {

    @Override
    public List<Product> apply(List<Product> products) {
        Map<Long, List<Product>> productsGroupByCategory = groupByCategory(products);
        return extractFirstPerCategory(productsGroupByCategory);
    }

    private Map<Long, List<Product>> groupByCategory(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(p -> p.getCategory().getId()));
    }

    private List<Product> extractFirstPerCategory(Map<Long, List<Product>> grouped) {
        return grouped.values().stream()
                .map(productList ->
                        productList.stream()
                                .min(Comparator.comparing(Product::getId))
                                .orElseThrow()
                )
                .toList();
    }

}
