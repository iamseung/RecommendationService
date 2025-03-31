package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;

import java.util.List;

public interface BrandSelectionStrategy {
    Long selectBrandId(List<BrandWithTotalPriceDto> candidates);
}
