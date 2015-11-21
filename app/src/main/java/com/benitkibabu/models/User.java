package com.benitkibabu.models;

/**
 * Created by Ben on 09/10/2015.
 */
public class User {
    String studentNo;
    String email;
    String courseName;
    String courseCode;

    public User(String studentNo, String email, String courseName, String courseCode) {
        this.studentNo = studentNo;
        this.email = email;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public String getEmail() {
        return email;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
