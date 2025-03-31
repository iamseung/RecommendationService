package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinMaxProductDto {
    private final Product minProduct;
    private final Product maxProduct;

}

