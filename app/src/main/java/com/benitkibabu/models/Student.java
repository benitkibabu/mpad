package com.benitkibabu.models;

/**
 * Created by Ben on 07/11/2015.
 */
public class Student {
    String studentID;
    String studentEmail;

    public Student(String studentID, String studentEmail) {
        this.studentID = studentID;
        this.studentEmail = studentEmail;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }
}
