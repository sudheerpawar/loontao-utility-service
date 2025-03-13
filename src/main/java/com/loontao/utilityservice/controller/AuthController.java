package com.loontao.utilityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loontao.utilityservice.dto.LoginResponse;
import com.loontao.utilityservice.dto.LoginUserDto;
import com.loontao.utilityservice.dto.RegisterUserDto;
import com.loontao.utilityservice.entity.User;
import com.loontao.utilityservice.service.AuthenticationService;
import com.loontao.utilityservice.service.JwtService;
import com.loontao.utilityservice.service.UserService;


@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserService userService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        // Sign up the user
        User registeredUser;
        // user phone number is mandatory for sign up
        if (registerUserDto == null || registerUserDto.getPhoneNumber() == null || registerUserDto.getPhoneNumber().isEmpty()) {
            return ResponseEntity.badRequest().body("User details are required. Phone number is mandatory for sign up.");
        }
        try {
            registeredUser = authenticationService.signup(registerUserDto);
            // Check if registration is successful
            if (registeredUser != null && (registeredUser.getPhoneNumber() != null || !registeredUser.getPhoneNumber().isEmpty())) {
                // Trigger the webhook
                userService.triggerWebhook(registeredUser.getPhoneNumber());
                return ResponseEntity.ok(registeredUser);
            } else if (registeredUser == null)
            {
                return ResponseEntity.status(500).body("User phone number " + registerUserDto.getPhoneNumber() +  " or " + registerUserDto.getEmailId() + " already exists. Please Login or try with other phone number or email Id.");
            } else {
                return ResponseEntity.badRequest().body("User registration failed. Please try again.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during registration: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
    
            if (authenticatedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
    
            if (!authenticatedUser.isEnabled()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User account is disabled");
            }
    
            String jwtToken = jwtService.generateToken(authenticatedUser);
    
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());
            loginResponse.setEmailId(authenticatedUser.getUsername());
            loginResponse.setPhoneNumber(authenticatedUser.getPhoneNumber());
            loginResponse.setRole(authenticatedUser.getRole().toString());
            loginResponse.setSuccess(true);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }

    @DeleteMapping("/delete/account")
    public ResponseEntity<?> deleteAccount(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
    
            if (authenticatedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
    
            if (!authenticatedUser.isEnabled()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User account is disabled");
            }
    
            boolean isDeleted = userService.deleteUser(authenticatedUser.getPhoneNumber());
    
            if (isDeleted) {
                return ResponseEntity.ok("User account deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during account deletion");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during account deletion");
        }
    }
}
