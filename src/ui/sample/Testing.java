package ui.sample;

import database.DataBaseManagement;
import models.Sex;
import models.Student;

public class Testing {
    public static void main(String[] args) {
        if (!DataBaseManagement.getInstance().openDataBase()) System.out.println("Couldn't open Database");
//        DataBaseManagement.getInstance().createTable("Student");
        Student student = new Student("ATR/4534/09", "Abebe", "Chala", 98764567,
                "12/12/1998","5 kilo", Sex.Male, 4);
        DataBaseManagement.getInstance().insertData(student);
        DataBaseManagement.getInstance().closeDataBase();

    }
}
