package com.example.recommendationservice.domain.dto.response;

import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BrandWithTotalDto {
    private final Brand brand;
    private final List<Product> products;

    public int getTotal() {
        return products.stream().mapToInt(Product::getPrice).sum();
    }
}
