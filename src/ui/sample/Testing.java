package ui.sample;

import database.Column;
import database.ColumnValue;
import database.DataBaseManagement;
import models.Sex;
import models.Student;

public class Testing {
    public static void main(String[] args) {
        if (!DataBaseManagement.getInstance().openDataBase()) System.out.println("Couldn't open Database");
//        DataBaseManagement.getInstance().createTable("Student");
    /*    Student student = new Student(, "Abebe", "Chala", ,
                "12/12/1998","5 kilo", Sex.Male, 4);*/
    DataBaseManagement.getInstance().createTable("Student",
            new Column("Id","String",10),
            new Column("first_name","String",15),
            new Column("last_name","String",15),
            new Column("phone_number","Int",0),
            new Column("dob","String",7),
            new Column("address","String",7),
            new Column("sex","String",7),
            new Column("year","int",7)
    );
    DataBaseManagement.getInstance().
                insertDataIntoTable("Student",
                        new ColumnValue<String>("\"ATR/4534/09\""),
                        new ColumnValue<String>("\"Abebe\""),
                        new ColumnValue<String>("\"Chala\""),
                        new ColumnValue<Integer>(98764567),
                        new ColumnValue<String>("\"12/12/1998\""),
                        new ColumnValue<String>("\"kilo\""),
                        new ColumnValue<String>("\"Male\""),
                        new ColumnValue<Integer>(4)
                        );
        DataBaseManagement.getInstance().closeDataBase();

    }
}
