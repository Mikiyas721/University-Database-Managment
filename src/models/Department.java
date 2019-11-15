package models;

import java.util.List;

public class Department {

    private String departmentName;
    private String departmentCode;
    private String buildingName;
    private int buildingNumber;
    private String officeNumber;
    private String college;
    private List<Integer> phoneNumbers;

    public Department(

            String departmentName,
            String code,
            String buildingName,
            int buildingNumber,
            String officeNumber,
            String college, List<Integer> phoneNumbers
    ) {
        this.departmentName = departmentName;
        this.departmentCode = code;
        this.buildingName = buildingName;
        this.buildingNumber = buildingNumber;
        this.officeNumber = officeNumber;
        this.college = college;
        this.phoneNumbers = phoneNumbers;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public List<Integer> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<Integer> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
