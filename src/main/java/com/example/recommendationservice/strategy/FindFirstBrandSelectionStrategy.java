package com.example.recommendationservice.strategy;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class FindFirstBrandSelectionStrategy implements BrandSelectionStrategy {

    @Override
    public Long selectBrandId(List<BrandWithTotalPriceDto> candidates) {
        return candidates.stream()
                .min(Comparator.comparingLong(BrandWithTotalPriceDto::getBrandId))
                .map(BrandWithTotalPriceDto::getBrandId)
                .orElseThrow();
    }
}
