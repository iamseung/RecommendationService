package com.example.recommendationservice.domain.model;

import com.example.recommendationservice.domain.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandModel {
    private String brandName;

    public Brand toEntity() {
        return Brand.of(brandName);
    }
}
