package com.mokpo.capstonedesign.retrofit2;

import com.google.gson.annotations.SerializedName;

public class IngredientAddResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("memo")
    private String memo;

    @SerializedName("count")
    private int count;

    @SerializedName("date")
    private String date;

    @SerializedName("user")
    private int user;
}
