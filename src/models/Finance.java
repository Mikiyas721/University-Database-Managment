package models;

public class Finance {

    private int fee;
    private String programid;
    private String collegeid;

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getCollegeid() {
        return collegeid;
    }

    public void setCollegeid(String collegeid) {
        this.collegeid = collegeid;
    }



    public Finance(int fee, String programid, String collegeid) {
        this.fee = fee;
        this.programid = programid;
        this.collegeid = collegeid;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }




}
