package com.loontao.utilityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false, name = "id")
        private Long id;

        @Column(nullable = false, name = "category")
        private String category;

        @Column(nullable = false, name = "mainText")
        private String mainText;

        @Column(nullable = false, name = "subText")
        private String subText;

        @Column(nullable = false, name = "fullText")
        private String fullText;
}
