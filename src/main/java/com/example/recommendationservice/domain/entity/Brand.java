package com.example.recommendationservice.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    private Brand(String name) {
        this.name = name;
    }

    private Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Brand of(String name) {
        return new Brand(name);
    }

    public static Brand of(Long id, String name) {
        return new Brand(id, name);
    }

    public void update(String categoryName) {
        if(categoryName != null) { this.name = categoryName; }
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}