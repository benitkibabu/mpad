package com.benitkibabu.models;

/**
 * Created by Ben on 19/11/2015.
 */
public class Timetable implements Comparable<Timetable>{
    private int id;
    private String day, module, lecturer, start, finish, room;

    public Timetable(int id, String day, String module, String lecturer, String start, String finish, String room) {
        this.id = id;
        this.day = day;
        this.module = module;
        this.lecturer = lecturer;
        this.start = start;
        this.finish = finish;
        this.room = room;
    }

    public Timetable(String day, String module, String lecturer, String start, String finish, String room) {
        this.day = day;
        this.module = module;
        this.lecturer = lecturer;
        this.start = start;
        this.finish = finish;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getModule() {
        return module;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getStart() {
        return start;
    }

    public String getFinish() {
        return finish;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public int compareTo(Timetable another) {
        return 0;
    }
}
