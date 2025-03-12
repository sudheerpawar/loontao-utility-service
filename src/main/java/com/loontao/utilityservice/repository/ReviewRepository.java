package com.loontao.utilityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loontao.utilityservice.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
