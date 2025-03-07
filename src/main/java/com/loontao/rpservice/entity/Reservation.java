package com.loontao.rpservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @NotNull(message = "Name is required")
    @Column(nullable = false, name = "name")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, name = "email")
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "Date is required")
    @Column(nullable = false, name = "date")
    private String date;

    @NotNull(message = "Time is required")
    @Column(nullable = false, name = "time")
    private String time;

    @NotNull(message = "Number of guests is required")
    @Min(value = 1, message = "At least one guest required")
    @Column(nullable = false, name = "seats_booked")
    private int numberOfGuests;

    @Column(name = "comments")
    private String comments;
}
