package com.mokpo.capstonedesign.ui.community;

public class Comment {
    private int id;
    private String content;
    private int board_id;
    private int user;

    private int parent_comment_id;

    // 생성자
    public Comment(int id, String content, String date, int user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    // 게터
  //  public int getId() { return id; }

  //  public String getContent() { return content; }

  //  public int getUser() { return user; }
}
