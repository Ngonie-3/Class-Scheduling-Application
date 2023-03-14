package model;

public class Lecturers {
    String lecturerID;
    String lecturerName;

    public Lecturers(String lecturerID, String lecturerName) {
        this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public String getLecturerName() {
        return lecturerName;
    }
}
