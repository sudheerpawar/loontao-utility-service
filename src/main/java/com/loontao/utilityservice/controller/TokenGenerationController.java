package com.loontao.utilityservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loontao.utilityservice.dto.JwtDTO;
import com.loontao.utilityservice.service.TokenGenerationService;

@RestController
@RequestMapping("/jwt")
public class TokenGenerationController {
    private final TokenGenerationService tokenGenerationService;
    
    public TokenGenerationController(TokenGenerationService tokenGenerationService) {
        this.tokenGenerationService = tokenGenerationService;
    }

    @GetMapping("/generate-token/{email}")
    public ResponseEntity<JwtDTO> generateTokenByEmail(@PathVariable String email) {
        try {
            return tokenGenerationService.generateTokenByEmail(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtDTO("An error occurred during token generation", null));
        }
    }   
}
