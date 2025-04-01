package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.MinMaxProductDto;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BrandSortedMinMaxStrategyTest {

    private final MinAndMaxCategorySelectionStrategy strategy = new BrandSortedMinMaxStrategy();

    @Test
    @DisplayName("가격이 같을 때, 브랜드 ID가 더 작은 상품이 최저가로 선택된다")
    void selectMinProductWhenPriceIsSame() {
        Category category = createCategory(1L, "상의");
        Brand brand1 = createBrand(1L, "브랜드A");
        Brand brand2 = createBrand(2L, "브랜드B");

        Product p1 = createProduct(1L, brand1, category, 1000);
        Product p2 = createProduct(2L, brand2, category, 1000);

        MinMaxProductDto result = strategy.apply(List.of(p1, p2));

        assertThat(result.getMinProduct()).isEqualTo(p1);
    }

    @Test
    @DisplayName("가격이 같을 때, 브랜드 ID가 더 작은 상품이 최고가로 선택된다")
    void selectMaxProductWhenPriceIsSame() {
        Category category = createCategory(1L, "하의");
        Brand brand1 = createBrand(1L, "브랜드A");
        Brand brand2 = createBrand(2L, "브랜드B");

        Product p1 = createProduct(1L, brand1, category, 5000);
        Product p2 = createProduct(2L, brand2, category, 5000);

        MinMaxProductDto result = strategy.apply(List.of(p1, p2));

        assertThat(result.getMaxProduct()).isEqualTo(p1);
    }

    @Test
    @DisplayName("가격과 브랜드 ID가 모두 다를 때, 가장 싼 상품과 가장 비싼 상품이 올바르게 선택된다")
    void selectMinAndMaxProductWithDifferentPriceAndBrand() {
        Category category = createCategory(1L, "아우터");
        Brand brand1 = createBrand(1L, "브랜드A");
        Brand brand2 = createBrand(2L, "브랜드B");
        Brand brand3 = createBrand(3L, "브랜드C");

        Product p1 = createProduct(1L, brand1, category, 1000);
        Product p2 = createProduct(2L, brand2, category, 3000);
        Product p3 = createProduct(3L, brand3, category, 5000);

        MinMaxProductDto result = strategy.apply(List.of(p1, p2, p3));

        assertThat(result.getMinProduct()).isEqualTo(p1);
        assertThat(result.getMaxProduct()).isEqualTo(p3);
    }

    @Test
    @DisplayName("상품 리스트가 비어 있으면 예외가 발생한다")
    void throwExceptionWhenProductListIsEmpty() {
        assertThatThrownBy(() -> strategy.apply(List.of()))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("상품 리스트가 존재하지 않습니다.");
    }

    public Brand createBrand(Long id, String name) {
        return Brand.of(id, name);
    }

    public Category createCategory(Long id, String name) {
        return Category.of(id, name);
    }

    public Product createProduct(Long id, Brand brand, Category category, int price) {
        return Product.of(id, brand, category, price);
    }
}