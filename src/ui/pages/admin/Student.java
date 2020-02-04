package ui.pages.admin;

import assistingclasses.Column;
import assistingclasses.ColumnValue;
import assistingclasses.Constants;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.Account.StudentAccount;
import ui.Validation;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

public class Student {
    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<StudentAccount> searchResults;
    private String userName = null;

    Student(BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        setWindowTop(toolBar);
        setWindowCenter();
        setWindowRight();
    }

    private void setWindowTop(ToolBar toolBar) {
        SearchTool searchTool = new SearchTool(Constants.STUDENT_ACCOUNT_INPUTS);
        searchTool.setOnSearch((observable, oldValue, newValue) -> {
            searchResults.setItem(DataBaseManagement.getInstance().fetchStudentAccountWithCondition(
                    Constants.getComparingColumn(searchTool.getSelectedRadioButton()), newValue));
        });
        window.setTop(new VBox(toolBar, searchTool.getSearchBar()));
    }


    private void setWindowRight() {
        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new Student ", "Submit", event -> onSubmitButtonClicked(), Constants.STUDENT_ACCOUNT_INPUTS);
        editExisting = new Inputs("Edit Student  Information", "Edit", event -> onEditButtonClicked(), Constants.STUDENT_ACCOUNT_INPUTS);
        VBox deleteAccount = new VBox(5);
        deleteAccount.setPadding(new Insets(10));

        Label label = new Label("Load or Remove Account");
        label.getStyleClass().add("title");
        label.getStylesheets().add("./ui/css/label.css");

        Button loadButton = new Button("Load");
        loadButton.setId("loadButton");
        loadButton.getStylesheets().add("./ui/css/label.css");
        loadButton.setOnAction(event -> onLoadButtonClicked());

        Button deleteButton = new Button("Delete");
        deleteButton.setId("deleteButton");
        deleteButton.getStylesheets().add("./ui/css/label.css");
        deleteButton.setOnAction(event -> onDeleteButtonClicked());


        deleteAccount.getChildren().addAll(label, loadButton, deleteButton);

        mainBox.getChildren().addAll(addNew.getGridPane(), new Separator(), editExisting.getGridPane(), new Separator(), deleteAccount);
        scrollPane.setContent(mainBox);
        scrollPane.setMinWidth(250);

        window.setRight(scrollPane);

    }

    private void setWindowCenter() {
        searchResults = new MyTableView<>(
                new MyTableColumn("First Name", "firstName"),
                new MyTableColumn("Last Name", "lastName"),
                new MyTableColumn("Email", "email"),
                new MyTableColumn("User Name", "userName"),
                new MyTableColumn("Password", "password")
        );
        DataBaseManagement.getInstance().createTable("StudentAccount",
                new Column("firstName", "String", 15),
                new Column("lastName", "String", 15),
                new Column("email", "String", 15),
                new Column("userName", "String", 15),
                new Column("password", "String", 15)
        );
        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudentAccount("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty StudentAccount List");
        }
        window.setCenter(searchResults.getTableView());


    }

    private void onSubmitButtonClicked() {
        if (Validation.validateName(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[0])) != null ||
                Validation.validateName(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[1])) != null) {
            addNew.setMessage(Validation.validateName(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[0])));
        } else if (Validation.validateUserName(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[3])) != null) {
            addNew.setMessage(Validation.validateUserName(addNew.getTextFieldValue((Constants.STUDENT_ACCOUNT_INPUTS[3]))));
        }/*else if (Validation.validatePassword(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[4]) !=null)){
            addNew.setMessage(Validation.validatePassword(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[4])));
        } else {*/
            DataBaseManagement.getInstance().insertDataIntoTable("StudentAccount",
                    new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[0]), "firstName"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[1]), "lastName"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[2]), "email"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[3]), "username"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[4]), "password")
            );

        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudentAccount("*"));


    }

    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("StudentAccount",
                "userName=\"" + userName + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[0]), "firstName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[1]), "lastName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[2]), "email"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[3]), "userName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[4]), "password")

        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudentAccount("*"));
    }

    private void onLoadButtonClicked() {
        ObservableList<StudentAccount> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(studentAccount -> {
            editExisting.setTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[0], studentAccount.getFirstName());
            editExisting.setTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[1], studentAccount.getLastName());
            editExisting.setTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[2], studentAccount.getEmail());
            editExisting.setTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[3], studentAccount.getUserName());
            editExisting.setTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[4], studentAccount.getPassword());
            userName = editExisting.getTextFieldValue(Constants.STUDENT_ACCOUNT_INPUTS[3]);
        });

    }

    private void onDeleteButtonClicked() {
        ObservableList<StudentAccount> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("StudentAccount",
                "username=\"" + account.getUserName() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudentAccount("*"));

    }

}


