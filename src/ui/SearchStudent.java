package ui;

import database.DataBaseManagement;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Sex;
import models.Student;


public class SearchStudent {
    private Application searchPage;

    SearchStudent() {
        searchPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                TableView<Student> searchResults = new TableView<>();
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
                        searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"))
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
                firstNameColumn.setMinWidth(200);
                firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

                TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
                lastNameColumn.setMinWidth(200);
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

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(5));
                vBox.getChildren().addAll(searchRow, searchResults);


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


}
