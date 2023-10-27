package com.mokpo.capstonedesign.ui.community;

public class Post {
    private int id;
    private String title;
    private String content;
    private String date;
    private int user;

    // 생성자
    public Post(int id, String title, String content, String date, int user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.user = user;
    }

    // 게터
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDate() { return date; }
    public int getUser() { return user; }
}
