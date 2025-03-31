package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.request.CreateBrandRequest;
import com.example.recommendationservice.domain.dto.request.UpdateBrandRequest;
import com.example.recommendationservice.domain.dto.response.ApiSuccessResponse;
import com.example.recommendationservice.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "브랜드 API", description = "브랜드 등록, 수정, 삭제 관련 API")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
public class BrandController {

    private final BrandService brandService;

    // 4-1. 브랜드 추가  API
    @Operation(summary = "Brand 등록", description = "브랜드 이름을 입력받아 새로운 브랜드를 등록합니다.")
    @PostMapping("/brand")
    public ResponseEntity<ApiSuccessResponse<Void>> createBrand(@RequestBody CreateBrandRequest dto) {
        brandService.createBrand(dto.toModel());
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 4-2. 브랜드 업데이트 API
    @Operation(summary = "Brand 수정", description = "브랜드 ID와 수정할 이름을 입력받아 브랜드 정보를 수정합니다.")
    @PutMapping("/brand/{brandId}")
    public ResponseEntity<ApiSuccessResponse<Void>> updateBrand(@PathVariable Long brandId,
                            @RequestBody UpdateBrandRequest dto) {
        brandService.updateBrand(brandId, dto.toModel());
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 4-3. 브랜드 삭제 API
    @Operation(summary = "Brand 삭제", description = "브랜드 ID를 입력받아 해당 브랜드를 삭제합니다.")
    @DeleteMapping("/brand/{brandId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }
}
