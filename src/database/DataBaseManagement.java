package database;

import assistingclasses.Column;
import assistingclasses.ColumnValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Account.Account;
import models.Account.RegistrarAccount;
import models.Sex;
import models.*;

import java.sql.*;

public class DataBaseManagement {
    private static DataBaseManagement ourInstance = new DataBaseManagement();
    private static final String CONNECTION_STRING = "jdbc:sqlite:Database\\University.db";
    private Connection connection;

    public static DataBaseManagement getInstance() {
        return ourInstance;
    }

    private DataBaseManagement() {
    }

    public boolean openDataBase() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeDataBase() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet fetchColumnsFromTable(String tableName, String comparingColumn, String newValue, String... columns) {
        String query = "SELECT ";
        boolean isFirst = true;
        for (String column : columns) {
            if (isFirst) {
                query += column;
                isFirst = false;
            } else {
                query += ("," + column);
            }
        }
        query += " FROM " + tableName;
        if (!newValue.equals("")) {
            query += " WHERE " + comparingColumn + " LIKE '%" + newValue + "%'";
        }
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error: From the fetch Columns Tables");
            return null;
        }
    }

    private ObservableList<Student> makeStudentObservable(ResultSet resultSet) {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Sex.getSexObject(resultSet.getString(4)),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        Student.getLocalDateFromString(resultSet.getString(7)),
                        resultSet.getInt(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12)
                );
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    private ObservableList<RegistrarAccount> makeRegistrarAccountObservable(ResultSet resultSet) {
        ObservableList<RegistrarAccount> accountList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                RegistrarAccount registrarAccount = new RegistrarAccount(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                accountList.add(registrarAccount);
            }
            return accountList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Student> fetchWithCondition(String comparingColumn, String newValue) {
        ResultSet resultSet = fetchColumnsFromTable("Student", comparingColumn, newValue, "*");
        return makeStudentObservable(resultSet);
    }

    public ObservableList<Student> fetchColumnsFromStudent(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("Student", "", "", columns);
        return makeStudentObservable(resultSet);
    }

    public ObservableList<RegistrarAccount> fetchColumnsFromRegistrarAccount(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("RegistrarAccount", "", "", columns);
        return makeRegistrarAccountObservable(resultSet);
    }

    public ObservableList<Course> fetchColumnsFromCourse(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("Course", "", "", columns);
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                courseList.add(course);
            }
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Department> fetchColumnsFromDepartment(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("Department", "", "", columns);
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Department department = new Department(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                        /*resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getArray(7)*/
                );
                departmentList.add(department);
            }
            return departmentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Section> fetchColumnsFromSection(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("Section", "", "", columns);
        ObservableList<Section> sectionList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Section section = new Section(
                        resultSet.getString(1).charAt(0),
                        resultSet.getInt(2)/*,
                        resultSet.getInt(3),
                        resultSet.getInt(4)*/

                );
                sectionList.add(section);
            }
            return sectionList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Dependants> fetchColumnsFromDependants(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("Section", "", "", columns);
        ObservableList<Dependants> sectionList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Dependants dependants = new Dependants(
                        resultSet.getString(1),
                        Sex.getSexObject(resultSet.getString(2)),
                        Student.getLocalDateFromString(resultSet.getString(3)),
                        resultSet.getString(4)
                );
                sectionList.add(dependants);
            }
            return sectionList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ObservableList<Account> fetchColumnsFromUserName(String... columns) {
        ResultSet resultSet = fetchColumnsFromTable("Account", "", "", columns);
        ObservableList<Account> accountList = FXCollections.observableArrayList();
        try {
            while (resultSet.next()) {
                Account account = new Account(
                        resultSet.getString(1),
                        resultSet.getString(2)
                );
                accountList.add(account);
            }
            return accountList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertDataIntoTable(String tableName, ColumnValue... values) {
        String query = "INSERT INTO " + tableName + " VALUES ( ";
        boolean isFirst = true;
        for (ColumnValue column : values) {
            if (isFirst) {
                query += "\"" + column.getValue() + "\"";
                isFirst = false;
            } else {
                query += (",\"" + column.getValue() + "\"");
            }
        }
        query += ")";
        try (Statement statement = connection.createStatement()) {
            return statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createTable(String tableName, Column... columns) {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName;
        boolean isFirst = true;
        for (Column column : columns) {
            if (isFirst) {
                query += "(" + column.getColumnName() + " " + column.getSQLDataType();
                isFirst = false;
            } else {
                query += "," + column.getColumnName() + " " + column.getSQLDataType();
            }
        }
        query += ")";
        try (Statement statement = connection.createStatement()
        ) {
            return statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteRowFromTable(String tableName, String condition) {
        String query = "DELETE FROM " + tableName + " WHERE " + condition;
        try (Statement statement = connection.createStatement()) {
            return statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateValueInTable(String tableName, String condition, ColumnValue... values) {
        String query = "UPDATE " + tableName + " SET ";
        boolean isFirst = true;
        for (ColumnValue column : values) {
            if (isFirst) {
                query += (column.getColumnName() + "=\"" + column.getValue() + "\"");
                isFirst = false;
            } else {
                query += ("," + column.getColumnName() + "=\"" + column.getValue() + "\"");
            }
        }
        query += " WHERE " + condition;
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
