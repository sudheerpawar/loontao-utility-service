package com.loontao.utilityservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.loontao.utilityservice.dto.RewardPointsResponseDTO;
import com.loontao.utilityservice.dto.UserRewardPointsRequestDTO;
import com.loontao.utilityservice.entity.UserRewardPointsEntity;
import com.loontao.utilityservice.repository.UserRepository;
import com.loontao.utilityservice.repository.UserRewardPointsRepository;  

@Service
public class UserRewardPointsService {
    
    private final UserRewardPointsRepository userRewardPointsRepository;
    private final UserRepository userRepository;

    public UserRewardPointsService(UserRewardPointsRepository userRewardPointsRepository, UserRepository userRepository) {
        this.userRewardPointsRepository = userRewardPointsRepository;
        this.userRepository = userRepository;
    }
    
    public Optional<UserRewardPointsEntity> getRewardPoints(String phoneNumber) {
        return userRewardPointsRepository.findByPhoneNumber(phoneNumber);
    }
    
    public RewardPointsResponseDTO addRewardPoints(UserRewardPointsRequestDTO userRewardPointsRequestDTO) {
        validateUserRewardPointsRequestDTO(userRewardPointsRequestDTO);
            
                RewardPointsResponseDTO response = new RewardPointsResponseDTO();
                String phoneNumber = userRewardPointsRequestDTO.getPhoneNumber();
                boolean userExists = userRepository.findByPhoneNumber(phoneNumber).isPresent();
            
                if (!userExists) {
                    response.setMessage("User needs to sign up with phone number: " + phoneNumber);
                }
            
                Optional<UserRewardPointsEntity> existingEntity = userRewardPointsRepository.findByPhoneNumber(phoneNumber);
            
                if (existingEntity.isPresent()) {
                    UserRewardPointsEntity userRewardPointsEntity = existingEntity.get();
                    userRewardPointsEntity.setRewardPoints(userRewardPointsRequestDTO.getRewardPoints());
                    userRewardPointsRepository.save(userRewardPointsEntity);
                } else {
                    UserRewardPointsEntity newEntity = new UserRewardPointsEntity();
                    newEntity.setPhoneNumber(phoneNumber);
                    newEntity.setRewardPoints(userRewardPointsRequestDTO.getRewardPoints());
                    userRewardPointsRepository.save(newEntity);
                }
                response.setSuccess(true);
                if (response.getMessage() == null) {
                    response.setMessage("Reward points added successfully.");
                }
                return response;
            }
        
            private void validateUserRewardPointsRequestDTO(UserRewardPointsRequestDTO UserRewardPointsRequestDTO) {
                if (UserRewardPointsRequestDTO == null) {
                    throw new IllegalArgumentException("UserRewardPointsRequestDTO cannot be null");
                }
                if (UserRewardPointsRequestDTO.getPhoneNumber() == null || UserRewardPointsRequestDTO.getPhoneNumber().trim().isEmpty()) {
                    throw new IllegalArgumentException("Phone number cannot be null or empty");
                }
                if (UserRewardPointsRequestDTO.getRewardPoints() < 0) {
                    throw new IllegalArgumentException("Reward points cannot be negative");
                }
            }
    
}