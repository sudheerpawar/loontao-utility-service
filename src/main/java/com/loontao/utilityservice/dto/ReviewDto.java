package com.loontao.utilityservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;

    private String comment;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be greater than or equal to 1")
    @Max(value = 5, message = "Rating must be less than or equal to 5")
    private int rating;
}

