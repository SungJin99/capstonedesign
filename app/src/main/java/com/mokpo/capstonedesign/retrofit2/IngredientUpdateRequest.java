package com.mokpo.capstonedesign.retrofit2;

import java.util.List;

public class IngredientUpdateRequest {


    private int id;
    private String name;
    private String memo;
    private int count;
    private String expiration_date;

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

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
    public IngredientUpdateRequest(int id, String name, String expiration_date, int count, String memo) {
        this.id = id;
        this.name = name;
        this.expiration_date = expiration_date;
        this.count = count;
        this.memo = memo;
    }

}
