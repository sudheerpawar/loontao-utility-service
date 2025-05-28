package com.loontao.utilityservice.dto;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private String category;
    private String mainText;
    private String subText;
    private String fullText;
}
