package com.loontao.utilityservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loontao.utilityservice.entity.User;
import com.loontao.utilityservice.entity.VerificationToken;
import com.loontao.utilityservice.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {
    
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    // Method to find a verification token by its token string
    public Optional<VerificationToken> findByToken(String token) {
        return Optional.ofNullable(verificationTokenRepository.findByToken(token));
    }

    // Method to find a verification token by the associated user
    public Optional<VerificationToken> findByUser(User user) {
        return Optional.ofNullable(verificationTokenRepository.findByUser(user));
    }

    /*
     * Method to create a verification token for a user.
     * @param user The user for whom the verification token is to be created.
     * This method checks if the user is not null and has a valid phone number before creating a new token.
     * It throws an IllegalArgumentException if the user is null or has a null phone number.
     * This is useful for generating a new verification token when a user registers or requests a new token.
     * It is annotated with @Transactional to ensure that the creation operation is performed within a transaction context.
     * This ensures that if the creation fails, the transaction can be rolled back to maintain data integrity.
     * It also checks if a verification token already exists for the user, and if so, deletes the existing token before creating a new one.
     * This prevents duplication of tokens for the same user.
     * It returns the newly created verification token.
     * This method is useful for ensuring that each user has a unique verification token at any given time.
     * 
     */
    @Transactional
    public VerificationToken createVerificationToken(User user) {
        // Validate the user object
        if (user == null || user.getPhoneNumber() == null) {
            throw new IllegalArgumentException("User cannot be null or have a null phone number");
        }
        // Check if a verification token already exists for the user
        Optional<VerificationToken> existingToken = findByUser(user);

        if (existingToken.isPresent()) {
            
            System.out.println("Updating existing token for user: " + user.getPhoneNumber());
            // If a token already exists, update new token and expiry date
            existingToken.get().resetToken(); // Reset the token and expiry date
            // Optionally, you can log this action or handle it as needed
            System.out.println("Existing verification token for user " + user.getPhoneNumber() + " has been updated.");
            return verificationTokenRepository.save(existingToken.get()); // Save the updated token on existing entity

        } else {
        // Create a new verification token for the user
        System.out.println("Creating new token for user: " + user.getPhoneNumber());
        VerificationToken verificationToken = new VerificationToken(user);
        return verificationTokenRepository.save(verificationToken);
    }
}

    /*
     * Method to delete a verification token.
     * @param verificationToken The verification token to delete.
     * This method checks if the verification token is not null and has a valid ID before attempting to delete it.
     * It throws an IllegalArgumentException if the verification token is null or has a null ID.
     * This is useful for cleaning up tokens that are no longer needed, such as after successful verification or when a user requests a new token.
     * It is annotated with @Transactional to ensure that the deletion operation is performed within a transaction context. 
     * This ensures that if the deletion fails, the transaction can be rolled back to maintain data integrity.
     */
    @Transactional
    public void deleteVerificationToken(VerificationToken verificationToken) {
        // Validate the verification token object
        if (verificationToken == null || verificationToken.getId() == null) {
            throw new IllegalArgumentException("Verification token cannot be null or have a null ID");
        }
        // Delete the verification token from the repository
        verificationTokenRepository.delete(verificationToken);
    }

    /*
     * Method to check if a verification token is expired.
     * @param verificationToken The verification token to check.
     * @return true if the token is expired, false otherwise.
     * This method checks if the current time is after the expiry date of the token.
     * It throws an IllegalArgumentException if the verification token is null or has a null expiry date.
     * This is useful for validating tokens before allowing actions that require a valid token.
     */
    public boolean isTokenExpired(VerificationToken verificationToken) {
        // Validate the verification token object
        if (verificationToken == null || verificationToken.getExpiryDate() == null) {
            throw new IllegalArgumentException("Verification token cannot be null or have a null expiry date");
        }
        // Check if the current time is after the expiry date
        return verificationToken.getExpiryDate().isBefore(java.time.LocalDateTime.now());
    }
}
