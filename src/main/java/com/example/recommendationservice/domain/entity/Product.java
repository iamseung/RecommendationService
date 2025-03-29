package com.example.recommendationservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

// 한 브랜드의 한 카테고리에 대한 상품은 1개만 있다고 가정
@Getter
@Setter
@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "category_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private Integer price;

    private Product(Brand brand, Category category, Integer price) {
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public static Product of(Brand brand, Category category, Integer price) {
        return new Product(brand, category, price);
    }

    public void update(Brand brand, Category category, Integer price) {
        if(brand != null) { this.brand = brand; }
        if(category != null) { this.category = category; }
        if(price != null) { this.price = price; }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", brand=" + brand +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
