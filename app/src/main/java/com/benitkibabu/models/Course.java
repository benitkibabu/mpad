package com.benitkibabu.models;

/**
 * Created by Ben on 07/11/2015.
 */
public class Course {

    String code, name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
