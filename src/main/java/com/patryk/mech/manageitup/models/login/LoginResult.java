package com.patryk.mech.manageitup.models.login;

import com.patryk.mech.manageitup.models.User;

import java.util.UUID;

public class LoginResult {
    private String accessToken;
    private String tokenType = "Bearer";
    private User.UserRoles role;
    private UUID userId;

    public LoginResult(String token, User.UserRoles role, UUID userId) {
        this.accessToken = token;
        this.role = role;
        this.userId = userId;
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
    //
//    public enum Result {
//        WRONG_PASSWORD,
//        NOT_REGISTERED,
//        SUCCESSFUL
//    }
//
//    private Result result;
//
//    public LoginResult() {
//        this(false, null);
//    }
//
//    public LoginResult(boolean isLogged, Result result) {
//        this.isLogged = isLogged;
//        this.result = result;
//    }
//
//    public boolean isLogged() {
//        return isLogged;
//    }
//
//    public void setLogged(boolean isLogged) {
//        this.isLogged = isLogged;
//    }
//
//    public void setResult(Result result) {
//        this.result = result;
//    }
//
//    public Result getResult() {
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "LoginResult{" +
//                "isLogged=" + isLogged +
//                ", result=" + result +
//                '}';
//    }
}
