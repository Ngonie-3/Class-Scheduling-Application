package model;

public class Course {
    String courseNumber;
    String courseName;
    int maxSeatingCapacity;
    String lecturers;

    public Course(String courseNumber, String courseName, int maxSeatingCapacity, String lecturers) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.maxSeatingCapacity = maxSeatingCapacity;
        this.lecturers = lecturers;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxSeatingCapacity() {
        return maxSeatingCapacity;
    }

    public String getLecturers() {
        return lecturers;
    }
}
