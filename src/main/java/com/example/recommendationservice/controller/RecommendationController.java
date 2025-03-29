package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.response.BrandRecommendationResponse;
import com.example.recommendationservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    @GetMapping("/product/min-price-by-category")
    public void getMinPriceProductByCategory() {
        productService.getMinPriceProducts();
    }

    // 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    @GetMapping("/brand/min-total-price")
    public BrandRecommendationResponse getBrandWithLowestTotalPrice() {
        return productService.getBrandWithLowestTotalPrice();
    }

    // 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    @GetMapping("/category/price-range")
    public void getPriceRangeByCategory(String categoryName) {
        productService.getMinAndMaxPriceProducts(categoryName);
    }

    // 4-1. 브랜드 추가  API
    @PostMapping("/brand")
    public void createBrand() {

    }

    // 4-2. 브랜드 업데이트 API
    @PutMapping("/brand")
    public void updateBrand() {

    }

    // 4-3. 브랜드 삭제 API
    @DeleteMapping("/brand/{id}")
    public void deleteBrand() {

    }

    // 5-1. 상품 추가 API
    @PostMapping("/product")
    public void createProduct() {

    }

    // 5-2. 상품 업데이트 API
    @PutMapping("/product")
    public void updateProduct() {

    }

    // 5-3. 상품 삭제 API
    @DeleteMapping("/product/{id}")
    public void deleteProduct() {

    }
}
