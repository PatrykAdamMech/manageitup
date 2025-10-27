package com.patryk.mech.manageitup.models.login;

public class LoginResult {
    private boolean isLogged;

    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResult(String token) { this.accessToken = token; }
    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
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
