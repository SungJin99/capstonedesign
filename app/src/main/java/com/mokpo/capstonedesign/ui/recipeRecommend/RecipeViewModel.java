package com.mokpo.capstonedesign.ui.recipeRecommend;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.RecipeResponse;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.Recipe;
import com.mokpo.capstonedesign.retrofit2.RecipeDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class RecipeViewModel extends ViewModel {

    private final MutableLiveData<List<Recipe>> recipes;
    private final SingleLiveEvent<RecipeDetail> recipeDetailLiveData;
    private Context context;

    public RecipeViewModel(Context context) {
        this.context = context;
        recipes = new MutableLiveData<>();
        recipeDetailLiveData = new SingleLiveEvent<>();
        loadRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
    public LiveData<RecipeDetail> getRecipeDetailLiveData() { // 추가
        return recipeDetailLiveData;
    }

    private void loadRecipes() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();

        Call<RecipeResponse> call = apiService.getRecommendedRecipes("Bearer " + accessToken);
        call.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recipes.setValue(response.body().getRecipes());

                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                // 에러 처리
                Log.e("API_CALL_ERROR", "Failed to load recipes", t);
            }
        });
    }
    public void fetchRecipeDetails(String recipeName) {
        ApiService apiService = ApiClient.getApiService();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        Call<RecipeDetail> call = apiService.getRecipeDetail("Bearer " + accessToken, recipeName);  // 받은 레시피 이름을 그대로 사용하여 요청을 보냅니다.

        call.enqueue(new Callback<RecipeDetail>() {
            @Override
            public void onResponse(Call<RecipeDetail> call, Response<RecipeDetail> response) {
                if (response.isSuccessful()) {
                    RecipeDetail recipeDetail = response.body();
                    // 상세 정보를 LiveData에 설정하는 로직
                    recipeDetailLiveData.setValue(recipeDetail);
                } else {
                    // 에러 처리
                    Log.e("ERROR", "Server response error");
                }
            }

            @Override
            public void onFailure(Call<RecipeDetail> call, Throwable t) {
                // 에러 처리
                Log.e("ERROR", "Network request error");
            }
        });
    }



}
