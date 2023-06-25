package com.mokpo.capstonedesign.retrofit2;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    String token;

    public String getToken() {
        return token;
    }
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}