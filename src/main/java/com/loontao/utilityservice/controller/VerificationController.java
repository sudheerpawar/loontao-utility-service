package com.loontao.utilityservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loontao.utilityservice.entity.User;
import com.loontao.utilityservice.entity.VerificationToken;
import com.loontao.utilityservice.repository.UserRepository;
import com.loontao.utilityservice.service.EmailService;
import com.loontao.utilityservice.service.VerificationTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/verification")
public class VerificationController {
    
    // Add a field to hold the base URL for email verification links
    @Value("${app.base.url}")
    private String appBaseUrl;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public VerificationController(VerificationTokenService verificationTokenService, EmailService emailService, UserRepository userRepository) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        // 1.  Find the token in the database
        Optional<VerificationToken> verificationTokenOptional = verificationTokenService.findByToken(token);
        
        // 2. If the token is not found, return a bad request response
        if (verificationTokenOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
        
        // Get the verification token from the optional
        // If the token is found, check if it is expired
        VerificationToken verificationToken = verificationTokenOptional.get();
        
        // 3. If the token is expired, return a bad request response
        if (verificationTokenService.isTokenExpired(verificationToken)) {
            // Delete the expired token.
            verificationTokenService.deleteVerificationToken(verificationToken);
            return ResponseEntity.badRequest().body("Verification token has expired.");
        }

        // 4. If the token is valid and not expired, "verify" the user by deleting the token
        verificationTokenService.deleteVerificationToken(verificationToken);
        
        // 5. Return a success response
        return ResponseEntity.ok("Email verification successful!");

    }
    
 
    @PostMapping("/internal/send-verification-email/{phoneNumber}")
    public ResponseEntity<String> sendVerificationEmailForUser(@PathVariable String phoneNumber) {
        int expirationMinutes = 30;
        // Find the user by their phone number (our primary key)
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User with phone number " + phoneNumber + " not found.");
        }
        // Get the user from the optional
        User user = userOptional.get();
        // Create a new token for this user
        VerificationToken token = verificationTokenService.createVerificationToken(user);

        // Construct the verification URL. The base URL should ideally come from application properties.
        String verificationUrl = appBaseUrl + "/verification/verify-email?token=" + token.getToken();

        // Send the email. This assumes your User entity has a getEmail() method.
        emailService.sendVerificationEmail(user.getUsername(), verificationUrl, user.getPhoneNumber(), expirationMinutes);

        return ResponseEntity.ok("Verification email sent to user: " + user.getUsername());
    }
    
}
