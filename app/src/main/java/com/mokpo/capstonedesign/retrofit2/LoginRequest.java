package com.mokpo.capstonedesign.retrofit2;

public class LoginRequest {
    String userid;
    String password;

    public LoginRequest(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}