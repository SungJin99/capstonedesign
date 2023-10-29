package com.mokpo.capstonedesign.ui.community;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private int id;

    @SerializedName("content")
    private String content;

    @SerializedName("board")
    private int board;

    @SerializedName("user")
    private int user;

    @SerializedName("date")
    private String date;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getUser(){return user;}

    public void setUser(int user){this.user = user;}

    public String getDate(){return date;}

    public void setDate(String date){this.date = date;}

    public int getBoard(){return board;}
    public void setBoard(int board){this.board = board;}
    public Comment(int id, String content, int board, int user) {
        this.id = id;
        this.content = content;
        this.board = board;
        this.user = user;
    }
    @Override
    public String toString() {
        return "[ID: " + id + ", Content: " + content+ ",  board: " + board+ ",  user: " + user + "]";  // 혹은 다른 원하는 내용을 반환할 수 있습니다.
    }
}

