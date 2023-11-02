package com.mokpo.capstonedesign.retrofit2;

public class IngredientResponse {
    private int id;
    private String name;
    private String memo;
    private int count;
    private String date;
    private int user;

    // 생성자
    public IngredientResponse(int id, String name, String memo, int count, String date, int user) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.count = count;
        this.date = date;
        this.user = user;
    }

    // 게터
    public int getId() { return id; }
    public String getName() { return name; }
    public String getMemo() { return memo; }
    public int getCount() { return count; }
    public String getDate() { return date; }
    public int getUser() { return user; }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser(int user) {
        this.user = user;
    }

}

