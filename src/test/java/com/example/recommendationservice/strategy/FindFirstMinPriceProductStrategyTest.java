package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FindFirstMinPriceProductStrategyTest {

    private final MinPriceCategorySelectionStrategy strategy = new FindFirstMinPriceProductStrategy();

    @Test
    @DisplayName("카테고리별로 상품을 그룹핑하고, 각 그룹에서 ID가 가장 작은 상품만 추출한다")
    void extractProductWithSmallestIdPerCategory() {
        Brand brand = createBrand(1L, "브랜드A");
        Category category1 = createCategory(1L, "상의");
        Category category2 = createCategory(2L, "하의");

        Product p1 = createProduct(10L, brand, category1, 1000);
        Product p2 = createProduct(5L, brand, category1, 1200);
        Product p3 = createProduct(3L, brand, category2, 1500);
        Product p4 = createProduct(8L, brand, category2, 1600);

        List<Product> result = strategy.apply(List.of(p1, p2, p3, p4));

        assertThat(result)
                .hasSize(2)
                .extracting("id")
                .containsExactlyInAnyOrder(5L, 3L); // 각 카테고리의 최소 ID
    }

    private Brand createBrand(Long id, String name) {
        return Brand.of(id, name);
    }

    private Category createCategory(Long id, String name) {
        return Category.of(id, name);
    }

    private Product createProduct(Long id, Brand brand, Category category, Integer price) {
        return Product.of(id, brand, category, price);
    }
}