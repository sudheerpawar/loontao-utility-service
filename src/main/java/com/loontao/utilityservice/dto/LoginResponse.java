package com.loontao.utilityservice.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private long expiresIn;
    private boolean success;
    private String emailId;
    private String phoneNumber;
    private String role;

public LoginResponse setToken(String token) {
    this.token = token;
    return this;
}

public LoginResponse setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
    return this;
}

public LoginResponse setEmailId(String emailId) {
    this.emailId = emailId;
    return this;
}

public LoginResponse setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
}

public LoginResponse setRole(String role) {
    this.role = role;
    return this;
}

public LoginResponse setSuccess(boolean success) {
    this.success = success;
    return this;
}
}

