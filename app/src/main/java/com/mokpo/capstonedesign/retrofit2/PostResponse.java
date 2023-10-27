package com.mokpo.capstonedesign.retrofit2;

public class PostResponse {
 private int id;
 private String title;
 private String content;
 private String date;
 private int user;



 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }

 public String getTitle(){
  return title;
 }
 public void setTitle(String title){
  this.title = title;
 }
 public String getContent(){
  return content;
 }
 public void setContent(String content){
  this.content = content;
 }
 public String getDate(){
  return date;
 }
 public void setDate(String date){
  this.date = date;
 }
 public int getUser(){
  return user;
 }
 public void setUser(int user){
  this.user = user;
 }
 // title, content, date, user에 대한 getter와 setter도 추가해 주세요.
}
