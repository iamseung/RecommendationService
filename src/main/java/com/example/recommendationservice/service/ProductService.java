package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.domain.model.ProductModel;

import java.util.List;

public interface ProductService {

    void createProduct(ProductModel productModel);
    void updateProduct(Long productId, ProductModel productModel);
    void deleteProduct(Long productId);

    List<Product> findMinPriceProductsByCategory();
    List<BrandWithTotalPriceDto> findBrandsWithLowestTotalPrice();

    List<Product> findProductsByCategoryId(Long categoryId);
    List<Product> findByBrandId(Long brandId);
}
