package models;

public class TuitionFee {
    private double fee;
    private String programId;
    private String collegeId;

    public TuitionFee(double fee,
                      String programId,
                      String collegeId) {
        this.fee = fee;
        this.programId = programId;
        this.collegeId = collegeId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
