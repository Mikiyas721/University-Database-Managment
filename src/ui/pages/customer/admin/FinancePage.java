package ui.pages.customer.admin;

import assistingclasses.Column;
import assistingclasses.ColumnValue;
import assistingclasses.Constants;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Finance;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.RadioButtonGrid;
import ui.customWidget.SearchTool;

public class FinancePage {

    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<Finance> searchResults;
    private String fee = null;

    FinancePage(BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        window.setTop(toolBar);
        setWindowCenter();
        setWindowRight();
    }

    private void setWindowRight() {
        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new tuition", "Submit", event -> onSubmitButtonClicked(), Constants.TUITION_INPUTS
        );
        editExisting = new Inputs("Edit Schedule info", "Edit", event -> onEditButtonClicked(), Constants.TUITION_INPUTS);

        VBox deleteAccount = new VBox(5);
        deleteAccount.setPadding(new Insets(10));

        Label label = new Label("Load or Remove schedule items");
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
                new MyTableColumn("Fee", "fee"),
                new MyTableColumn("Program", "programid"),
                new MyTableColumn("College", "collegeid")
        );

        DataBaseManagement.getInstance().createTable("Finance",
                new Column("fee", "Int", 15),
                new Column("Program Id", "String", 15),
                new Column("College Id", "String", 15)
        );
        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromFinanceTable("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty finance List");
        }
        window.setCenter(searchResults.getTableView());
    }

    private void onSubmitButtonClicked() {
        {
            DataBaseManagement.getInstance().insertDataIntoTable("Finance",
                    new ColumnValue(addNew.getTextFieldValue(Constants.TUITION_INPUTS[0]), "fee"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.TUITION_INPUTS[1]), "programid"),
                    new ColumnValue(addNew.getTextFieldValue(Constants.TUITION_INPUTS[2]), "collegeid")

            );
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromFinanceTable("*"));
        }
    }

    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("Finance",
                "fee=\"" + fee + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.TUITION_INPUTS[0]), "fee"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.TUITION_INPUTS[1]), "Program"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.TUITION_INPUTS[2]), "College")


        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromFinanceTable("*"));
    }


    private void onLoadButtonClicked() {
        ObservableList<Finance> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(Finance -> {
            editExisting.setTextFieldValue(Constants.TUITION_INPUTS[0], Integer.toString(Finance.getFee()));
            editExisting.setTextFieldValue(Constants.TUITION_INPUTS[1], Finance.getProgramid());
            editExisting.setTextFieldValue(Constants.TUITION_INPUTS[2], Finance.getCollegeid());

        });

    }

    private void onDeleteButtonClicked() {
        ObservableList<models.Finance> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("FinanceTable",
                "programid=\"" + account.getProgramid() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromFinanceTable("*"));

    }


}
