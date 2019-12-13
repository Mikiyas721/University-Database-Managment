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

    public ObservableList<Student> fetchAllData() {
        String query = "SELECT * FROM students";
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
                        Sex.makeSexObject(resultSet.getString(7)),
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

    public boolean insertData(Student student) {
        String query = "INSERT INTO student VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setInt(4, student.getPhoneNumber());
            preparedStatement.setString(5, student.getDataOfBirth());
            preparedStatement.setString(6, student.getAddress());
            preparedStatement.setString(7, String.format("%s", student.getSex()));
            preparedStatement.setInt(8, student.getYear());


            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createTable(String tableName) {//TODO change fix number of parameter to variable number of args
        String query = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(id varchar(15)," +
                "first_name varchar(15)," +
                "last_name varchar(15)," +
                "phone_number int," +
                "dataOfBirth varchar(15)," +
                "address varchar(15)," +
                "sex varchar (6)," +
                "acadamic_year int)";
        try (Statement statement = connection.createStatement()
        ) {
            return statement.execute(query);
           /* if (result == 1) return true;
            return false;*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
