package models;

public enum Sex {
    Male,
    Female;

    public static Sex makeSexObject(String sex) {
        if (sex.equals("Male")) return Sex.Male;
        else return Sex.Female;
    }

}
