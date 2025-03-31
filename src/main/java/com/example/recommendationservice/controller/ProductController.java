package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.request.CreateProductRequest;
import com.example.recommendationservice.domain.dto.request.UpdateProductResquest;
import com.example.recommendationservice.domain.dto.response.ApiSuccessResponse;
import com.example.recommendationservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 API", description = "상품 등록, 수정, 삭제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // 5-1. 상품 추가 API
    @Operation(summary = "상품 등록", description = "브랜드 ID, 카테고리 ID, 가격을 입력받아 상품을 등록합니다.")
    @PostMapping("/product")
    public ResponseEntity<ApiSuccessResponse<Void>> createProduct(@RequestBody CreateProductRequest dto) {
        productService.createProduct(dto.toModel());
        return ResponseEntity.status(201).body(ApiSuccessResponse.ok());
    }

    // 5-2. 상품 업데이트 API
    @Operation(summary = "상품 수정", description = "상품 ID와 변경할 브랜드 ID, 카테고리 ID, 가격을 입력받아 상품 정보를 수정합니다.")
    @PutMapping("/product/{productId}")
    public ResponseEntity<ApiSuccessResponse<Void>> updateProduct(@PathVariable Long productId,
                              @RequestBody UpdateProductResquest dto) {
        productService.updateProduct(productId, dto.toModel());
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 5-3. 상품 삭제 API
    @Operation(summary = "상품 삭제", description = "상품 ID를 입력받아 해당 상품을 삭제합니다.")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }
}
