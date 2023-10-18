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
    @POST("api/user/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


    @GET("api/food/get/")
    Call<List<IngredientResponse>> getFoodList(@Header("Authorization")String token);

    @POST("api/food/put_ingredient/")
    Call<IngredientAddResponse> getInsertfood(@Header("Authorization")String token, @Body IngredientAddRequest request);

    @FormUrlEncoded
    @POST("api/food/updt_ingredient/")
    Call<IngredientUpdateResponse> getUpdateFood(@Header("Authorization")String token,
                                                 @Field("id") int id,
                                                 @Field("name") String name,
                                                 @Field("expiry") String expiry,
                                                 @Field("quantity") int quantity,
                                                 @Field("memo") String memo);
}

