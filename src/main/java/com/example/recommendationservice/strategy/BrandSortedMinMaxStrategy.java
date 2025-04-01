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

    @Override
    public MinMaxProductDto apply(List<Product> products) {
        Product minProduct = products.stream()
                .min(minPriceComparator())
                .orElseThrow(() -> new BaseException(PRODUCT_LIST_IS_EMPTY, "상품 리스트가 존재하지 않습니다."));

        Product maxProduct = products.stream()
                .max(maxPriceComparator())
                .orElseThrow(() -> new BaseException(PRODUCT_LIST_IS_EMPTY, "상품 리스트가 존재하지 않습니다."));

        return new MinMaxProductDto(minProduct, maxProduct);
    }

    private Comparator<Product> minPriceComparator() {
        return Comparator.comparingInt(Product::getPrice)
                .thenComparing(p -> p.getBrand().getId());
    }

    private Comparator<Product> maxPriceComparator() {
        return Comparator.comparingInt(Product::getPrice).reversed()
                .thenComparing(p -> p.getBrand().getId());
    }
}
