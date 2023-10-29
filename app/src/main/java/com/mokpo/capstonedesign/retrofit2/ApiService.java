package com.mokpo.capstonedesign.retrofit2;

import com.mokpo.capstonedesign.ui.community.Comment;
import com.mokpo.capstonedesign.ui.community.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/user/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //식재료 관리
    @GET("api/food/get/")
    Call<List<IngredientResponse>> getFoodList(@Header("Authorization")String token);

    @POST("api/food/put_ingredient/")
    Call<IngredientAddResponse> getInsertfood(@Header("Authorization")String token, @Body IngredientAddRequest request);

    @FormUrlEncoded
    @POST("api/food/updt_ingredient/")
    Call<IngredientUpdateResponse> getUpdateFood(@Header("Authorization")String token,
                                                 @Field("id") int id,
                                                 @Field("name") String name,
                                                 @Field("memo") String memo,
                                                 @Field("count") int quantity,
                                                 @Field("date") String date);

    @POST("api/food/del_ingredients/")
    Call<IngredientDeleteResponse> DeleteFood(@Header("Authorization")String token,
                                                 @Body DeleteIngredients request);
    // 커뮤니티
    @GET("api/post/list_post/")
    Call<ArrayList<Post>> getPostList(@Header("Authorization")String token);

    @POST("api/post/create_post/")
    Call<PostResponse> createPost(@Header("Authorization")String token, @Body PostRequest request);
    @GET("api/post/get_post/{id}/")
    Call<Post> getPost(@Path("id") int id);

    @FormUrlEncoded
    @POST("api/post/update_post/{id}/")
    Call<Post> updatePost(@Header("Authorization")String token, @Path("id") int id,
                          @Field("title")String title,
                          @Field("content")String content);

    @POST("api/post/del_post/{id}/")
    Call<Void> deletePost(@Header("Authorization")String token, @Path("id") int id);

    @POST("api/post/del_comment/{id}/")
    Call<Void> deleteComment(@Header("Authorization")String token, @Path("id")int id);
}

