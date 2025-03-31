package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.model.BrandModel;

public interface BrandService {
    void createBrand(BrandModel brandModel);
    void updateBrand(Long brandId, BrandModel brandModel);
    void deleteBrand(Long brandId);
    Brand findById(Long brandId);
}
