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
    String status;
    String firstName;
    String lastName;

    public Student(String studentID, String studentEmail,  String password, String reg_id, String course) {
        this.studentID = studentID;
        this.studentEmail = studentEmail;
        this.reg_id = reg_id;
        this.password = password;
        this.course = course;
    }

    public Student(String studentID, String studentEmail, String reg_id, String password, String course, String status) {
        this.studentID = studentID;
        this.studentEmail = studentEmail;
        this.reg_id = reg_id;
        this.password = password;
        this.course = course;
        this.status = status;
    }

    public Student(String studentID, String studentEmail, String reg_id, String password, String course, String status, String firstName, String lastName) {
        this.studentID = studentID;
        this.studentEmail = studentEmail;
        this.reg_id = reg_id;
        this.password = password;
        this.course = course;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
