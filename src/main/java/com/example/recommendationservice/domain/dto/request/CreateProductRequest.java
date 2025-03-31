package com.example.recommendationservice.domain.dto.request;

import com.example.recommendationservice.domain.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NonNull
    private Long brandId;
    @NonNull
    private Long categoryId;
    @NonNull
    private Integer price;

    public ProductModel toModel() {
        return new ProductModel(brandId, categoryId, price);
    }
}
