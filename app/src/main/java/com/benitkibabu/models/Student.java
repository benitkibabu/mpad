package com.benitkibabu.models;

/**
 * Created by Ben on 07/11/2015.
 */
public class Student {
    String studentID;
    String studentEmail;
    String reg_id;
    String password;
    String course;

    public Student(String studentID, String studentEmail,  String password, String reg_id, String course) {
        this.studentID = studentID;
        this.studentEmail = studentEmail;
        this.reg_id = reg_id;
        this.password = password;
        this.course = course;
    }

    public String getReg_id() {
        return reg_id;
    }

    public String getPassword() {
        return password;
    }

    public String getCourse() {
        return course;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }
}
