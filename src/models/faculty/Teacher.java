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
        courses_Id = courses_id;
        sections_Id = sections_id;
    }

}
