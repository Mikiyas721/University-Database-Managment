package models;

public class Section {

    private char SectionCode;
    private int year;
    private int course_Id;
    private int teacher_Id;


    public Section(
            char sectionCode,
            int year,
            int course_Id,
            int teacher_Id
    ) {
        SectionCode = sectionCode;
        this.year = year;
        this.course_Id = course_Id;
        this.teacher_Id = teacher_Id;
    }

    public char getSectionCode() {
        return SectionCode;
    }

    public void setSectionCode(char sectionCode) {
        SectionCode = sectionCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public int getTeacher_Id() {
        return teacher_Id;
    }

    public void setTeacher_Id(int teacher_Id) {
        this.teacher_Id = teacher_Id;
    }



}
