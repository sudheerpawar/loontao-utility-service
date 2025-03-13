package com.loontao.utilityservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.loontao.utilityservice.dto.JwtDTO;
import com.loontao.utilityservice.exceptions.ResourceNotFoundException;
import com.loontao.utilityservice.repository.UserRepository;

@Service
public class TokenGenerationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public TokenGenerationService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<JwtDTO> generateTokenByEmail(String email) {
        if (userRepository.findByEmailId(email).isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not registered.");
        }
        String jwtToken = jwtService.generateToken(userRepository.findByEmailId(email).get());
        return ResponseEntity.ok(new JwtDTO(jwtToken, jwtService.extractExpiration(jwtToken).toString()));
    }   
}
