package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.MinMaxProductDto;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.exception.BaseException;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

import static com.example.recommendationservice.exception.ErrorCode.PRODUCT_LIST_IS_EMPTY;

@Component
public class BrandSortedMinMaxStrategy implements MinAndMaxCategorySelectionStrategy {

    private static final String EMPTY_PRODUCT_LIST_MESSAGE = "상품 리스트가 존재하지 않습니다.";

    @Override
    public MinMaxProductDto apply(List<Product> products) {
        Product minProduct = findMinProduct(products);
        Product maxProduct = findMaxProduct(products);

        return new MinMaxProductDto(minProduct, maxProduct);
    }

    private Product findMinProduct(List<Product> products) {
        return products.stream()
                .min(Comparator.comparingInt(Product::getPrice)
                        .thenComparing(p -> p.getBrand().getId()))
                .orElseThrow(() -> new BaseException(PRODUCT_LIST_IS_EMPTY, EMPTY_PRODUCT_LIST_MESSAGE));
    }

    private Product findMaxProduct(List<Product> products) {
        int maxPrice = getMaxPrice(products);

        return products.stream()
                .filter(p -> p.getPrice() == maxPrice)
                .min(Comparator.comparing(p -> p.getBrand().getId()))
                .orElseThrow(() -> new BaseException(PRODUCT_LIST_IS_EMPTY, EMPTY_PRODUCT_LIST_MESSAGE));
    }

    private int getMaxPrice(List<Product> products) {
        return products.stream()
                .mapToInt(Product::getPrice)
                .max()
                .orElseThrow(() -> new BaseException(PRODUCT_LIST_IS_EMPTY, EMPTY_PRODUCT_LIST_MESSAGE));
    }
}
