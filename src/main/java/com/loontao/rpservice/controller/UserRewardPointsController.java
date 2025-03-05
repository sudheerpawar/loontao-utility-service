package com.loontao.rpservice.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.loontao.rpservice.dto.RewardPointsResponseDTO;
import com.loontao.rpservice.dto.UserRewardPointsRequestDTO;
import com.loontao.rpservice.entity.UserRewardPointsEntity;
import com.loontao.rpservice.exceptions.UserNotFoundException;
import com.loontao.rpservice.service.UserRewardPointsService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/rwdpts")
@RestController
public class UserRewardPointsController {
    
    private final UserRewardPointsService userRewardPointsService;

    public UserRewardPointsController(UserRewardPointsService userRewardPointsService) {
        this.userRewardPointsService = userRewardPointsService;
    }
    
    @GetMapping("/getrwdpts")
    public Optional<UserRewardPointsEntity> getRewardPoints(@RequestParam String phoneNumber) {
        try {
            Optional<UserRewardPointsEntity> rewardPoints = null;
            rewardPoints = userRewardPointsService.getRewardPoints(phoneNumber);
            return rewardPoints;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @PostMapping("/addrwdpts")
    public RewardPointsResponseDTO addRewardPoints(@RequestBody UserRewardPointsRequestDTO userRewardPointsRequestDTO) {
        try {
            return userRewardPointsService.addRewardPoints(userRewardPointsRequestDTO);
        } catch (IllegalArgumentException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

}
