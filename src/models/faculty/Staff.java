package models.faculty;

import models.Sex;

abstract public class Staff {

    private String firstName;
    private String lastName;
    private String id;
    private int phoneNumber;
    private String address;
    private Sex sex;
    private double salary;
    private String officeNumber;


    public Staff(
            String firstName,
            String lastName,
            String id,
            int phoneNumber,
            String address,
            Sex sex,
            double salary,
            String officeNumber
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sex = sex;
        this.salary = salary;
        this.officeNumber = officeNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getFullName() {
        return firstName + lastName;
    }
}
