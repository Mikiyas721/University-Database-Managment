package models;

import java.time.DayOfWeek;
import java.time.LocalTime;

// DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
//LocalTime.now().format(formatter)
public class Schedule {
    private String teacherId;
    private String courseNumber;
    private String sectionCode;
    private LocalTime startTime;
    private LocalTime endTime;
    private String departmentCode;
    private String CollegeId;
    private DayOfWeek dayOfWeek;
    private String classRoom;
    private String bldgName;
    private int bldgNumber;

    public Schedule(
            String courseNumber,
            LocalTime startTime,
            LocalTime endTime,
            DayOfWeek dayOfWeek,
            String classRoom,
            String bldgName
            ) {
        this.courseNumber = courseNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.classRoom = classRoom;
        this.bldgName = bldgName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getCollegeId() {
        return CollegeId;
    }

    public void setCollegeId(String collegeId) {
        CollegeId = collegeId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getBldgName() {
        return bldgName;
    }

    public void setBldgName(String bldgName) {
        this.bldgName = bldgName;
    }

    public int getBldgNumber() {
        return bldgNumber;
    }

    public void setBldgNumber(int bldgNumber) {
        this.bldgNumber = bldgNumber;
    }
}
