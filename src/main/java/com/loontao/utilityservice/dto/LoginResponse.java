package com.loontao.utilityservice.dto;

import com.loontao.utilityservice.entity.User;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private long expiresIn;
    private User authenticatedUser;
    private boolean success;

public LoginResponse setToken(String token) {
    this.token = token;
    return this;
}

public LoginResponse setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
    return this;
}

public LoginResponse setAuthenticatedUser(User authenticatedUser) {
    this.authenticatedUser = authenticatedUser;
    return this;
}

public void setSuccess(boolean success) {
    this.success = success;
}
}

