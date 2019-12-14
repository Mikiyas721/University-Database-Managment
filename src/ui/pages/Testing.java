package ui.pages;

import database.Column;
import database.ColumnValue;
import database.DataBaseManagement;
import javafx.collections.ObservableList;
import models.Sex;
import models.Student;

public class Testing {
/*    public static void main(String[] args) {
        if (!DataBaseManagement.getInstance().openDataBase()) System.out.println("Couldn't open Database");
        DataBaseManagement.getInstance().createTable("Student");
        Student student = new Student(, "Abebe", "Chala", ,
                "12/12/1998","5 kilo", Sex.Male, 4);
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
                        new ColumnValue<String>("\"ATR/4534/09\"",""),
                        new ColumnValue<String>("\"Abebe\"",""),
                        new ColumnValue<String>("\"Chala\"",""),
                        new ColumnValue<Integer>(98764567,""),
                        new ColumnValue<String>("\"12/12/1998\"",""),
                        new ColumnValue<String>("\"kilo\"",""),
                        new ColumnValue<String>("\"Male\"",""),
                        new ColumnValue<Integer>(4,"")
                        );
        DataBaseManagement.getInstance().updateValueInTable("Student", "Id = \"ATR/4534/09\"", new ColumnValue<>("Kebede", "first_name"));
        DataBaseManagement.getInstance().deleteRowFromTable("Student","Id = \"ATR/4534/09\"");
        ObservableList<Student> studentList = DataBaseManagement.getInstance().fetchColumnsFromStudent("*");
        for (Student student : studentList) {
            System.out.print(student.getId()+"  ");
            System.out.print(student.getFirstName()+"  ");
            System.out.print(student.getLastName()+"  ");
            System.out.print(student.getPhoneNumber()+"  ");
            System.out.print(student.getAddress()+"  ");
            System.out.print(student.getDataOfBirth()+"  ");
            System.out.print(student.getSex()+"  ");
            System.out.println(student.getYear());
        }
        DataBaseManagement.getInstance().closeDataBase();
    }*/
}
