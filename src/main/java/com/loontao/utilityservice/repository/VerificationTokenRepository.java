package com.loontao.utilityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loontao.utilityservice.entity.User;
import com.loontao.utilityservice.entity.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    //derived query methods to find VerificationToken by token or user
    // These methods will be automatically implemented by Spring Data JPA based on the method names
    VerificationToken findByToken(String token); // Method to find a verification token by its token string
    VerificationToken findByUser(User user); // Method to find a verification token by the associated user

}
// Note: The method names should match the property names in the VerificationToken entity.