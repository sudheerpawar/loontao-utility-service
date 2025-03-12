package com.loontao.utilityservice.dto;

import lombok.Data;

@Data
public class ReservationDTO {

    private String name;
    private String email;
    private String phoneNumber;
    private String date;
    private String time;
    private int numberOfGuests;
    private String comments;
}
