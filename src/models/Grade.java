package models;
//TODO GradeWindow is a weak entity, no primary key attributes of its own
public class Grade {
    private String letterGrade;
    private int numericGrade;
    private String courseCode;
    private String studentId;
    private String teacherId;

    public Grade(String letterGrade, int numericGrade, String courseCode,
                 String studentId, String teacherId) {
        this.letterGrade = letterGrade;
        this.numericGrade = numericGrade;
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public int getNumericGrade() {
        return numericGrade;
    }

    public void setNumericGrade(int numericGrade) {
        this.numericGrade = numericGrade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
