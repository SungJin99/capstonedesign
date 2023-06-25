package com.mokpo.capstonedesign.retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @FormUrlEncoded
    @POST("api/login/")
    Call<TokenResponse> loginUser(
            @Field("userid") String userid,
            @Field("password") String password
    );

    @GET("api/get/")
    Call<List<IngredientResponse>> getFoodList(@Header("Authorization")String token);

    @POST("api/putingredient/")
    Call<List<IngredientResponse>> ingredientAdd(@Header("Authorization") String token, @Body IngredientAddRequest IngredientAddRequest);
}

