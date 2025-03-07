package com.loontao.rpservice.controller;

import com.loontao.rpservice.dto.ReviewDto;
import com.loontao.rpservice.entity.Review;
import com.loontao.rpservice.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService service;
    public ReviewController(ReviewService service) { this.service = service; }

    @PostMapping("/add/{phoneNumber}")
    public Review createReview(@PathVariable String phoneNumber,@Valid @RequestBody ReviewDto reviewDto) {
        reviewDto.setPhoneNumber(phoneNumber);
        return service.addReview(reviewDto);
    }

    @GetMapping
    public List<Review> getReviews() {
        return service.getAllReviews();
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id,@Valid @RequestBody ReviewDto reviewDto) {
        return service.updateReview(id, reviewDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
    }
}
