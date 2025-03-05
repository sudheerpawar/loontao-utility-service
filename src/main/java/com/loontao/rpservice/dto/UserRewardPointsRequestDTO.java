package com.loontao.rpservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRewardPointsRequestDTO {

    private String emailId;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Reward points is required")
    @Min(value = 0, message = "Reward points must be greater than or equal to 0")
    private int rewardPoints;
 
    // getters and setters here...
}
