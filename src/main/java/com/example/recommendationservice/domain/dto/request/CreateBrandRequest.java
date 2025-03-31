package com.example.recommendationservice.domain.dto.request;


import com.example.recommendationservice.domain.model.BrandModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBrandRequest {
    private String brandName;

    public BrandModel toModel() {
        return new BrandModel(brandName);
    }
}
