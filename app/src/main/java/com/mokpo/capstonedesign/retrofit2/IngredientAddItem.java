package com.mokpo.capstonedesign.retrofit2;

import com.google.gson.annotations.SerializedName;

public class IngredientAddItem {

    @SerializedName("name")
    private String name;

    @SerializedName("memo")
    private String memo;

    @SerializedName("count")
    private int count;

    @SerializedName("date")
    private String date;
    public IngredientAddItem(String name, String memo, int count, String date) {
        this.name = name;
        this.memo = memo;
        this.count = count;
        this.date = date;
    }

}
