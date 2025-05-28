package com.loontao.utilityservice.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RestaurantInfoDTO {

    private Long id;
    private String category;
    private String content1;
    private String content2;
    private String content3;
}
