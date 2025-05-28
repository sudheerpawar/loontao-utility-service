package com.loontao.utilityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_data")
public class RestaurantInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column(nullable = false, name = "category")
    private String category;

    @Column(nullable = false, name = "content1")
    private String content1;

    @Column(nullable = false, name = "content2")
    private String content2;

    @Column(nullable = false, name = "content3")
    private String content3;

}
