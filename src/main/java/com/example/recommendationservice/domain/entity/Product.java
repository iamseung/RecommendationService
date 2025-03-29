package com.example.recommendationservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "product", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brand_id", "category_id"})
})
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
