package models.faculty;

import models.Sex;

import java.time.LocalDate;


public class Teacher extends Staff {


    private String rank;
    private int department_Id;
    private int courses_Id;
    /**
     * These fields have already been mapped to their own table
     * so they no longer belong here
     */
    private int sections_Id;

    public Teacher(
            String firstName,
            String lastName,
            String id,
            Sex sex,
            LocalDate dateOfBirth,
            int phoneNumber,
            String city,
            String subCity,
            String street,
            int houseNo,
            double salary,
            String officeNumber,
            String rank
    ) {
        super(
                id,
                firstName,
                lastName,
                dateOfBirth,
                sex,
                phoneNumber,
                houseNo,
                street,
                subCity,
                city,
                salary,
                officeNumber);
        this.rank = rank;
        /* this.department_Id = department_Id;*/
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getDepartment_Id() {
        return department_Id;
    }

    public void setDepartment_Id(int department_Id) {
        this.department_Id = department_Id;
    }
}
