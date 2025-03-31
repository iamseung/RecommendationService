package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.request.CreateBrandRequest;
import com.example.recommendationservice.domain.dto.request.UpdateBrandRequest;
import com.example.recommendationservice.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BrandController.class)
class BrandControllerTest {

    // TODO, 주입에 대해서
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private BrandService brandService;

    @DisplayName("[API][POST] 브랜드 등록 - 성공")
    @Test
    void givenCreateBrandDto_whenCreatingBrand_thenReturnsOk() throws Exception {
        // Given
        CreateBrandRequest dto = new CreateBrandRequest("Test Brand");
        willDoNothing().given(brandService).createBrand(any());

        // When & Then
        mvc.perform(post("/api/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        then(brandService).should().createBrand(any());
    }

    @DisplayName("[API][PUT] 브랜드 수정 - 성공")
    @Test
    void givenUpdateBrandDto_whenUpdatingBrand_thenReturnsOk() throws Exception {
        // Given
        Long brandId = 1L;
        UpdateBrandRequest dto = new UpdateBrandRequest("Updated Brand");
        willDoNothing().given(brandService).updateBrand(any(), any());

        // When & Then
        mvc.perform(put("/api/brand/" + brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        then(brandService).should().updateBrand(any(), any());
    }

    @DisplayName("[API][DELETE] 브랜드 삭제 - 성공")
    @Test
    void givenBrandId_whenDeletingBrand_thenReturnsOk() throws Exception {
        // Given
        Long brandId = 1L;
        willDoNothing().given(brandService).deleteBrand(brandId);

        // When & Then
        mvc.perform(delete("/api/brand/" + brandId))
                .andExpect(status().isOk());

        then(brandService).should().deleteBrand(brandId);
    }
}
