package com.xiayule.itstime.domain;

public class Memo {
    private String content;
    private String date;
    private int id;

    public Memo(String date, String content) {
        this.content = content;
        this.date = date;
    }

    public Memo(int id, String date, String content) {
        this(date, content);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "id: " + id + " content: " + content + " date:" + date;
    }
}
