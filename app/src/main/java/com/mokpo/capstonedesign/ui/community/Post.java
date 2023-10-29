package com.mokpo.capstonedesign.ui.community;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    private int id;
    private String title;
    private String content;
    private String date;
    private int user;
    @SerializedName("comments")
    private List<Comment> comments;

    // 생성자
    public Post(int id, String title, String content, String date, int user, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.user = user;
        this.comments = comments;
    }

    // 게터
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDate() { return date; }
    public int getUser() { return user; }
    public List<Comment> getComments() {
        return comments;
    }

    public List<Comment> getCommentsUser() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
