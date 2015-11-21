package com.benitkibabu.models;

/**
 * Created by Ben on 29/09/2015.
 */
public class ProfileItem {
    String studentName;
    String courseName;

    public ProfileItem(String studentName, String courseName){
        super();
        this.courseName = courseName;
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
