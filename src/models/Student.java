package models;

public class Student {

    private String firstName;
    private String lastName;
    private String id;
    private int phoneNumber;
    private String dataOfBirth;
    private String address;
    private Sex sex;
    private int year;
    private int department_id;
    private int course_id;

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public Student(
            String firstName,
            String lastName,
            String id,
            int phoneNumber,
            String dataOfBirth,
            String address,
            Sex sex,
            int year,
            int department_id1, int department_id
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.dataOfBirth = dataOfBirth;
        this.address = address;
        this.sex = sex;
        this.year = year;
        this.department_id = department_id1;
        this.course_id = department_id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(String dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDepartment() {
        return course_id;
    }

    public void setDepartment(int department_id) {
        this.course_id = department_id;
    }
    public String getFullName(){
        return firstName+lastName;
    }

}
