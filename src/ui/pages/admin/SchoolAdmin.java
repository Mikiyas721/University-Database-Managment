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
import models.Account.SchoolAdminAccount;
import ui.Validation;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

public class SchoolAdmin {
    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<SchoolAdminAccount> searchResults;
    private String userName = null;


    SchoolAdmin (BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        setWindowTop(toolBar);
        setWindowCenter();
        setWindowRight();
    }
    private void setWindowTop(ToolBar toolBar) {
        SearchTool searchTool = new SearchTool(Constants.SCHOOL_ADMIN_INPUTS);
        searchTool.setOnSearch((observable, oldValue, newValue) -> {
            searchResults.setItem(DataBaseManagement.getInstance().fetchSchoolAdminAccountWithCondition(
                    Constants.getComparingColumn(searchTool.getSelectedRadioButton()), newValue));
        });
        window.setTop(new VBox(toolBar, searchTool.getSearchBar()));
    }


    private void setWindowRight() {
        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new SchoolAdmin", "Submit", event -> onSubmitButtonClicked(), Constants.SCHOOL_ADMIN_INPUTS);
        editExisting = new Inputs("Edit SchoolAdmin  Information", "Edit", event -> onEditButtonClicked(), Constants.SCHOOL_ADMIN_INPUTS);
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
        DataBaseManagement.getInstance().createTable("SchoolAdminAccount",
                new Column("firstName", "String", 15),
                new Column("lastName", "String", 15),
                new Column("email", "String", 15),
                new Column("userName", "String", 15),
                new Column("password", "String", 15)
        );
        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromSchoolAdminAccount("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty SchoolAdminAccount List");
        }
        window.setCenter(searchResults.getTableView());


    }

    private void onSubmitButtonClicked() {
        if (Validation.validateName(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[0])) != null ||
                Validation.validateName(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[1])) != null) {
            addNew.setMessage(Validation.validateName(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[0])));
        } else if(Validation.validateUserName(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[3])) !=null ){
            addNew.setMessage(Validation.validateUserName(addNew.getTextFieldValue((Constants.SCHOOL_ADMIN_INPUTS[3]))));
        }/*else if (Validation.validatePassword(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[4]) !=null)){
            addNew.setMessage(Validation.validatePassword(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[4])));
        }*/
        else {
            DataBaseManagement.getInstance().insertDataIntoTable("SchoolAdminAccount",
                    new ColumnValue(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[0]), "firstName"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[1]), "lastName"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[2]), "email"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[3]), "username"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[4]), "password")
            );
        }
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromSchoolAdminAccount("*"));



    }

    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("SchoolAdminAccount",
                "userName=\"" + userName + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[0]), "firstName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[1]), "lastName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[2]), "email"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[3]), "userName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[4]), "password")

        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromSchoolAdminAccount("*"));
    }

    private void onLoadButtonClicked() {
        ObservableList<SchoolAdminAccount> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(schoolAdminAccount -> {
            editExisting.setTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[0], schoolAdminAccount.getFirstName());
            editExisting.setTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[1], schoolAdminAccount.getLastName());
            editExisting.setTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[2], schoolAdminAccount.getEmail());
            editExisting.setTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[3], schoolAdminAccount.getUserName());
            editExisting.setTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[4], schoolAdminAccount.getPassword());
            userName = editExisting.getTextFieldValue(Constants.SCHOOL_ADMIN_INPUTS[3]);
        });

    }

    private void onDeleteButtonClicked() {
        ObservableList<SchoolAdminAccount> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("SchoolAdminAccount",
                "username=\"" + account.getUserName() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromSchoolAdminAccount("*"));

    }





}
