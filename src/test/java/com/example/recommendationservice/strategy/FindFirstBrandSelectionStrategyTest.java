package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FindFirstBrandSelectionStrategyTest {

    private final BrandSelectionStrategy strategy = new FindFirstBrandSelectionStrategy();

    @Test
    @DisplayName("브랜드 ID가 가장 작은 후보의 ID를 반환한다")
    void returnSmallestBrandId() {
        BrandWithTotalPriceDto b1 = createBrandWithTotalPriceDto(3L, 10000);
        BrandWithTotalPriceDto b2 = createBrandWithTotalPriceDto(1L, 20000);
        BrandWithTotalPriceDto b3 = createBrandWithTotalPriceDto(5L, 15000);

        Long result = strategy.selectBrandId(List.of(b1, b2, b3));

        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("브랜드 ID가 하나뿐이면 해당 ID를 그대로 반환한다")
    void returnSingleBrandId() {
        BrandWithTotalPriceDto only = createBrandWithTotalPriceDto(1L, 9999);

        Long result = strategy.selectBrandId(List.of(only));

        assertThat(result).isEqualTo(1L);
    }

    public BrandWithTotalPriceDto createBrandWithTotalPriceDto(Long brandId, int totalPrice) {
        return new BrandWithTotalPriceDto() {
            @Override public Long getBrandId() { return brandId; }
            @Override public Integer getTotalPrice() { return totalPrice; }
        };
    }
}