package com.benitkibabu.models;

/**
 * Created by Ben on 30/09/2015.
 */
public class UpdateItem {
    String id;
    String title;
    String body;
    String target;
    String date;

    public UpdateItem(String id, String title, String body, String target, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.target = target;
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

    public String getTarget() {
        return target;
    }

    public String getDate() {
        return date;
    }

}
