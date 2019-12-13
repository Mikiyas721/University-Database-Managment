package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Sex;
import models.Student;

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

    public ObservableList<Student> fetchColumnsFromTable(String tableName, String... columns) {
        String query = "SELECT";
        boolean isFirst = true;
        for (String column : columns) {
            if (isFirst) {
                query += column;
                isFirst = false;
            } else {
                query += ("," + column);
            }
        }
        query += "FROM " + tableName;
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            ObservableList<Student> studentList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        Sex.getSexObject(resultSet.getString(7)),
                        resultSet.getInt(8)
//                        resultSet.getInt(9),
//                        resultSet.getInt(10)
                );
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertDataIntoTable(String tableName, ColumnValue... values /*Student student*/) {
        String query = "INSERT INTO " + tableName + " VALUES (";
        boolean isFirst = true;
        for (ColumnValue column : values) {
            if (isFirst) {
                query += column.getValue();
                isFirst = false;
            } else {
                query += ("," + column.getValue());
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

}
