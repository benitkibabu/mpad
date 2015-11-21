package com.benitkibabu.models;

/**
 * Created by Ben on 30/09/2015.
 */
public class NewsItem {
    String id;
    String title;
    String body;
    String newsType;
    String date;

    public NewsItem(String title, String body){
        super();
        this.title = title;
        this.body = body;
    }

    public NewsItem(String title, String body, String newsType, String date) {
        this.title = title;
        this.body = body;
        this.newsType = newsType;
        this.date = date;
    }

    public NewsItem(String id, String title, String body, String newsType, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.newsType = newsType;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getId() {
        return id;
    }

    public String getNewsType() {
        return newsType;
    }

    public String getDate() {
        return date;
    }

}
