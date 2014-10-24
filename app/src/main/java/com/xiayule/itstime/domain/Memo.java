package com.xiayule.itstime.domain;

public class Memo {
    private String content;
    private String date;
    private int id;
    private boolean isFinished;

    public Memo(String date, String content, boolean isFinished) {
        this.content = content;
        this.date = date;
        this.isFinished = isFinished;
    }

    public Memo(int id, String date, String content, boolean isFinished) {
        this(date, content, isFinished);
        this.id = id;
    }

    public Memo(String content, boolean isFinished) {
        this(null, content, isFinished);
    }

    public Memo(String date, String content) {
        this(date, content, false);
    }

    public Memo(int id, String date, String content) {
        this(id, date, content, false);
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

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "id: " + id + " content: " + content + " date:" + date + "isfinished: " + isFinished;
    }
}
