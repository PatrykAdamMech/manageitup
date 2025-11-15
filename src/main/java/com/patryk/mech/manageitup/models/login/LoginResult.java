package com.patryk.mech.manageitup.models.login;

import com.patryk.mech.manageitup.models.User;

import java.util.UUID;

public class LoginResult {
    private String accessToken;
    private String tokenType = "Bearer";
    private User.UserRoles role;
    private UUID userId;
    private String failReason;

    public LoginResult(String token, User.UserRoles role, UUID userId, String failReason) {
        this.accessToken = token;
        this.role = role;
        this.userId = userId;
        this.failReason = failReason;
    }
    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }

    public User.UserRoles getRole() {
        return role;
    }

    public void setRole(User.UserRoles role) {
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
