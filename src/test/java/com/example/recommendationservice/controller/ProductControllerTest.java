package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.request.CreateProductRequest;
import com.example.recommendationservice.domain.dto.request.UpdateProductResquest;
import com.example.recommendationservice.service.ProductService;
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

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean private ProductService productService;

    public ProductControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][POST] 상품 등록 - 성공")
    @Test
    void givenCreateProductDto_whenCreatingProduct_thenReturnsCreated() throws Exception {
        // Given
        CreateProductRequest dto = new CreateProductRequest(1L, 2L, 10000);
        willDoNothing().given(productService).createProduct(any());

        // When & Then
        mvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        then(productService).should().createProduct(any());
    }

    @DisplayName("[API][PUT] 상품 수정 - 성공")
    @Test
    void givenUpdateProductDto_whenUpdatingProduct_thenReturnsOk() throws Exception {
        // Given
        Long productId = 1L;
        UpdateProductResquest dto = new UpdateProductResquest(2L, 3L, 20000);
        willDoNothing().given(productService).updateProduct(any(), any());

        // When & Then
        mvc.perform(put("/api/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        then(productService).should().updateProduct(any(), any());
    }

    @DisplayName("[API][DELETE] 상품 삭제 - 성공")
    @Test
    void givenProductId_whenDeletingProduct_thenReturnsOk() throws Exception {
        // Given
        Long productId = 1L;
        willDoNothing().given(productService).deleteProduct(productId);

        // When & Then
        mvc.perform(delete("/api/product/" + productId))
                .andExpect(status().isOk());

        then(productService).should().deleteProduct(productId);
    }
}
