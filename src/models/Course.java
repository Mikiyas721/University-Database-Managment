package models;

public class Course {
    private String name;
    private String courseNumber;
    private String description;
    private double semesterHours;
    /*private int department_id;*/

    public Course(
            String name,
            String courseNumber,
            String description,
            double semesterHours
            /*int department_id*/
    ) {
        this.name = name;
        this.courseNumber = courseNumber;
        this.description = description;
        this.semesterHours = semesterHours;
        /* this.department_id = department_id;*/
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSemesterHours() {
        return semesterHours;
    }

    public void setSemesterHours(double semesterHours) {
        this.semesterHours = semesterHours;
    }
}
