package com.loontao.rpservice.service;

import com.loontao.rpservice.dto.ReviewDto;
import com.loontao.rpservice.entity.Review;
import com.loontao.rpservice.exceptions.ResourceNotFoundException;
import com.loontao.rpservice.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewService {

    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public Review addReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setPhoneNumber(reviewDto.getPhoneNumber());
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        return repository.save(review);
    }

    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    public Review updateReview(Long id, ReviewDto reviewDto) {
        if (repository.existsById(id)) {
            Review review = repository.findById(id).orElse(null);
            if (review != null) {
                review.setComment(reviewDto.getComment());
                review.setRating(reviewDto.getRating());
                return repository.save(review);
            }
            else throw new ResourceNotFoundException("Review with ID: " + id + " not found.");
        }
        return null;
    }

    public void deleteReview(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourceNotFoundException("Review with ID: " + id + " not found.");
        }
        repository.deleteById(id);
    }
}

