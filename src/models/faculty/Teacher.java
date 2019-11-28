package models.faculty;
import models.Sex;


public class Teacher extends Staff {

    private int department_Id;
    private String rank;
    private int courses_Id;
    private int sections_Id;

    public Teacher(

            String firstName,
            String lastName,
            String id,
            int phoneNumber,
            String address,
            Sex sex,
            double salary,
            String officeNumber,
            int department_Id,
            String rank, int courses_id, int sections_id) {
        super(firstName, lastName, id, phoneNumber, address, sex, salary, officeNumber);
        this.department_Id = department_Id;
        this.rank = rank;
        this.courses_Id = courses_id;
        this.sections_Id = sections_id;
    }

    public int getDepartment_Id() {
        return department_Id;
    }

    public void setDepartment_Id(int department_Id) {
        this.department_Id = department_Id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getCourses_Id() {
        return courses_Id;
    }

    public void setCourses_Id(int courses_Id) {
        this.courses_Id = courses_Id;
    }

    public int getSections_Id() {
        return sections_Id;
    }

    public void setSections_Id(int sections_Id) {
        this.sections_Id = sections_Id;
    }
}
