package com.example.meca.model;

import java.io.Serializable;

public class Maintain implements Serializable {
    private String content;
    private String date;
    public Maintain(String content, String date) {
        this.content = content;
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
