package model;

import javafx.scene.control.CheckBox;

public class Schedule {
    int classNumber;
    String department;
    String course;
    String room;
    String lecturer;
    String meetingTime;
    CheckBox checkBox;

    public Schedule(int classNumber, String department, String course, String room, String lecturer, String meetingTime, CheckBox checkBox) {
        this.classNumber = classNumber;
        this.department = department;
        this.course = course;
        this.room = room;
        this.lecturer = lecturer;
        this.meetingTime = meetingTime;
        this.checkBox = checkBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getCourse() {
        return course;
    }

    public String getRoom() {
        return room;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getMeetingTime() {
        return meetingTime;
    }
}
