package com.example.recommendationservice.domain.dto.request;

import com.example.recommendationservice.domain.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductResquest {

    private Long brandId;
    private Long categoryId;
    private Integer price;

    public ProductModel toModel() {
        return new ProductModel(brandId, categoryId, price);
    }
}