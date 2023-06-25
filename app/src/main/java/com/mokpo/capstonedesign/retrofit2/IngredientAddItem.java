package com.mokpo.capstonedesign.retrofit2;

import com.google.gson.annotations.SerializedName;

public class IngredientAddItem {

    @SerializedName("name")
    private String name;

    @SerializedName("memo")
    private String memo;

    @SerializedName("count")
    private String count;

    @SerializedName("date")
    private String date;
    public IngredientAddItem(String name, String memo, String count, String date) {
        this.name = name;
        this.memo = memo;
        this.count = count;
        this.date = date;
    }

}
