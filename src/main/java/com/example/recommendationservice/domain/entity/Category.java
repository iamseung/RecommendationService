package com.example.recommendationservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 예: 상의, 아우터 ...

    private Category(String name) {
        this.name = name;
    }

    private Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category of(String name) {
        return new Category(name);
    }

    public static Category of(Long id, String name) {
        return new Category(id, name);
    }
}
