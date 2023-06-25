package com.mokpo.capstonedesign.retrofit2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientAddRequest {
    @SerializedName("foodlist")
    private List<IngredientAddItem> ingredientAdd;

    public IngredientAddRequest(List<IngredientAddItem> ingredientAdd) {
        this.ingredientAdd = ingredientAdd;
    }

    // 게터 및 세터 메서드를 추가합니다.
    public List<IngredientAddItem> getIngredientAdd() {
        return ingredientAdd;
    }

    public void setIngredientAdd(List<IngredientAddItem> ingredientAdd) {
        this.ingredientAdd = ingredientAdd;
    }
}

