/*
package ui.sample;

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


public class SearchStudent extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        VBox checkBoxs = new VBox();
        checkBoxs.setSpacing(5);

        CheckBox searchByName = new CheckBox("Name");
        searchByName.setSelected(true);
        CheckBox searchByID = new CheckBox("ID");
        searchByID.setSelected(false);

        TextField search = new TextField();
        search.setMinWidth(400);
        search.setPromptText("Name");


        checkBoxs.getChildren().addAll(searchByID, searchByName);
        Button searchButton = new Button("Search");

        HBox searchRow = new HBox();
        searchRow.setSpacing(5);
        searchRow.getChildren().addAll(search, searchButton, checkBoxs);

        TableView<Student> searchResults = new TableView<>();

        TableColumn<Student, Integer> noColumn = new TableColumn<>("#");
        noColumn.setMaxWidth(30);
        noColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        TableColumn<Student, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setMinWidth(200);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(200);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Student, String> idColumn = new TableColumn<>("ID");
        idColumn.setMaxWidth(150);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, Sex> sexColumn = new TableColumn<>("Sex");
        sexColumn.setMaxWidth(40);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

        TableColumn<Student, String> yearColumn = new TableColumn<>("Year");
        yearColumn.setMaxWidth(50);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Student, Integer> phoneNumberColumn = new TableColumn<>("Phone Number");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Student, String> dataOfBirthColumn = new TableColumn<>("DOB");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("dataOfBirth"));

        TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("address"));


        searchResults.getColumns().addAll(noColumn, firstNameColumn, lastNameColumn, idColumn, sexColumn, yearColumn, phoneNumberColumn, dataOfBirthColumn, addressColumn);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5));
        vBox.getChildren().addAll(searchRow, searchResults);


        Scene newScene = new Scene(vBox, 800, 500);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Search");
        primaryStage.show();
    }
}
*/
