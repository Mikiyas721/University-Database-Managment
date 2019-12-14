package ui.pages;

import database.DataBaseManagement;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Sex;
import models.Student;

import java.sql.SQLException;


public class SearchStudent implements UpdateListener {
    private AddStudent add;

    public void showAddStudentDialog() {
        add = new AddStudent();
        add.setUpdateListener(this);
    }

    private Application searchPage;
    private TableView<Student> searchResults;
    private Label message;

    SearchStudent() {
        searchPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                searchResults = new TableView<>();
                VBox checkBoxs = new VBox();
                checkBoxs.setSpacing(5);

                CheckBox searchByName = new CheckBox("Name");
                searchByName.setSelected(true);
                CheckBox searchByID = new CheckBox("ID");
                searchByID.setSelected(false);

                TextField search = new TextField();
                search.setMinWidth(400);
                search.setPromptText("Name");
                search.textProperty().addListener((observable, oldValue, newValue) ->
                        searchResults.setItems(DataBaseManagement.getInstance().fetchWithCondition("firstName", newValue))
                );


                checkBoxs.getChildren().addAll(searchByID, searchByName);
                Button searchButton = new Button("Search");

                HBox searchRow = new HBox();
                searchRow.setSpacing(5);
                searchRow.getChildren().addAll(search, searchButton, checkBoxs);


                TableColumn<Student, Integer> noColumn = new TableColumn<>("#");
                noColumn.setMaxWidth(30);
                noColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

                TableColumn<Student, String> firstNameColumn = new TableColumn<>("First Name");
                firstNameColumn.setMinWidth(150);
                firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

                TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
                lastNameColumn.setMinWidth(150);
                lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                TableColumn<Student, String> idColumn = new TableColumn<>("ID");
                idColumn.setMaxWidth(150);
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Student, Sex> sexColumn = new TableColumn<>("Sex");
                sexColumn.setMaxWidth(60);
                sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

                TableColumn<Student, String> yearColumn = new TableColumn<>("Year");
                yearColumn.setMaxWidth(50);
                yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

                TableColumn<Student, Integer> phoneNumberColumn = new TableColumn<>("Phone Number");
                phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

                TableColumn<Student, String> dataOfBirthColumn = new TableColumn<>("DOB");
                dataOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dataOfBirth"));

                TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
                addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

                searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
                searchResults.getColumns().addAll(noColumn, firstNameColumn, lastNameColumn, idColumn, sexColumn, yearColumn, phoneNumberColumn, dataOfBirthColumn, addressColumn);

                HBox operations = new HBox();
                Button addStudent = new Button("Add");
                addStudent.setOnAction(event -> showAddStudentDialog());

                Button editStudent = new Button("Edit");
                editStudent.setOnAction(event -> {
                    ObservableList<Student> selected = searchResults.getSelectionModel().getSelectedItems();
                    if (selected.size() == 0) message.setText("Please select a book first");
                    else if (selected.size() > 1) message.setText("You can only update one book at a time");
                    else {
                        selected.forEach(student ->
                            showAddStudentDialog()
                        );
                      //TODO Finish using the Update method in the databaseManagment class and the Add UI
                    }
                });
                Button removeStudent = new Button("Remove");
                removeStudent.setOnAction(event -> {
                    ObservableList<Student> selected = searchResults.getSelectionModel().getSelectedItems();
                    selected.forEach(Student ->
                            DataBaseManagement.getInstance().deleteRowFromTable("Student", "id=\"" + Student.getId() + "\"")
                    );
                    searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
                });
                operations.getChildren().addAll(addStudent, editStudent, removeStudent);
                operations.setSpacing(5);
                operations.setPadding(new Insets(10));

                message = new Label();
                message.setId("messageLabel");
                message.getStylesheets().add("./ui/css/label.css");

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(5));
                vBox.getChildren().addAll(searchRow, searchResults, operations, message);


                Scene newScene = new Scene(vBox, 800, 500);
                primaryStage.setScene(newScene);
                primaryStage.setTitle("Search");
                primaryStage.show();
            }

        };
        try {
            searchPage.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Application getSearchPage() {
        return searchPage;
    }


    @Override
    public void updateTable() {
        searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }
}
