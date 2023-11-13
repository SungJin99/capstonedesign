//package com.mokpo.capstonedesign.retrofit2;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import androidx.annotation.Nullable;
//import androidx.preference.PreferenceManager;
//
//import java.io.IOException;
//
//import okhttp3.Authenticator;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.Route;
//import retrofit2.Call;
//
//public class TokenAuthenticator implements Authenticator {
//
//    private final Context context;
//    private final ApiService apiService;
//
//    public TokenAuthenticator(Context context, ApiService apiService) {
//        this.context = context;
//        this.apiService = apiService;
//    }
//
//    @Nullable
//    @Override
//    public Request authenticate(Route route, Response response) throws IOException {
//        if (response.code() == 401) {
//            String refreshToken = getRefreshToken(context);
//            Call<RefreshToken> call = apiService.getAccessToken("Bearer " + refreshToken, refreshToken);
//            Response<RefreshToken> tokenResponse = call.execute();
//
//            if (tokenResponse.isSuccessful()) {
//                String newAccessToken = tokenResponse.body().getAccessToken();
//                saveJwtToken(context, newAccessToken);
//
//                // 새로 발급받은 토큰으로 요청을 다시 만듭니다.
//                return response.request().newBuilder()
//                        .header("Authorization", "Bearer " + newAccessToken)
//                        .build();
//            }
//        }
//
//        // 401 이외의 다른 HTTP 상태 코드를 받았을 때는 null을 반환합니다.
//        // 이 경우 Retrofit은 요청을 중단하고 onFailure()를 호출합니다.
//        return null;
//    }
//
//
//
//    private void saveRefreshToken(Context context, String refreshToken) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("refresh_token", refreshToken);
//        editor.apply();
//    }
//
//    private String getJwtTokenFromPreferences(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return sharedPreferences.getString("jwt_token", null);
//    }
//}
