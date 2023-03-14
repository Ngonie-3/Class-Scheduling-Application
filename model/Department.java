package model;

public class Department {
    String departmentName;
    String departmentCourses;

    public Department(String departmentName, String departmentCourses) {
        this.departmentName = departmentName;
        this.departmentCourses = departmentCourses;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentCourses() {
        return departmentCourses;
    }
}
