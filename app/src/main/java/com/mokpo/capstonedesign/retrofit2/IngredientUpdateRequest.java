package com.mokpo.capstonedesign.retrofit2;

import java.util.List;

public class IngredientUpdateRequest {


    private int id;
    private String name;
    private String memo;
    private int count;
    private String date;

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public IngredientUpdateRequest(int id, String name, String date, int count, String memo) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.count = count;
        this.memo = memo;
    }

}
