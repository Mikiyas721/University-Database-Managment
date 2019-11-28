package models;

public class Dependants {
    private String name;
    private Sex sex;
    private String relationShip;
    private String dateOfBirth;

    public Dependants(String name, Sex sex, String relationShip, String dateOfBirth) {
        this.name = name;
        this.sex = sex;
        this.relationShip = relationShip;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
