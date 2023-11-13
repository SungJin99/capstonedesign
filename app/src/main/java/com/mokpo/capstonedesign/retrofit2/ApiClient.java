package com.mokpo.capstonedesign.retrofit2;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://20.249.62.24:8000/";
    private static Retrofit retrofit = null;

//    public static Retrofit getRetrofitInstance(Context context) {
//        // ...
//        // TokenAuthenticator 객체를 생성할 때 Context 객체를 전달
//        // context 변수는 이 메소드를 호출하는 측에서 제공해야 합니다.
//        client.authenticator(new TokenAuthenticator(context, getApiService()));
//        // ...
//    }


    public static ApiService getApiService() {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
//                    .authenticator(new TokenAuthenticator())
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit.create(ApiService.class);
    }
}
