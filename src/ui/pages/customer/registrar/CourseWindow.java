package ui.pages.customer.registrar;

import assistingclasses.Column;
import assistingclasses.ColumnValue;
import assistingclasses.Constants;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Course;
import models.faculty.Teacher;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.RadioButtonGrid;
import ui.customWidget.SearchTool;

public class CourseWindow {

    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<Course> searchResults;
    private String id = null;

    CourseWindow(BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        setWindowTop(toolBar);
        window.setLeft(null);
        setWindowCenter();
        setWindowRight();
    }

    private void setWindowTop(ToolBar toolBar) {
        SearchTool searchTool = new SearchTool(Constants.COURSE_INPUTS);
        searchTool.setOnSearch((observable, oldValue, newValue) -> {
            searchResults.setItem(DataBaseManagement.getInstance().fetchCourseWithCondition(Constants.getComparingColumn(searchTool.getSelectedRadioButton()), newValue));
        });
        window.setTop(new VBox(toolBar, searchTool.getSearchBar()));
    }

    private void setWindowRight() {
        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new course", "Submit", event -> onSubmitButtonClicked(), Constants.COURSE_INPUTS
        );
        editExisting = new Inputs("Edit course information", "Edit", event -> onEditButtonClicked(), Constants.COURSE_INPUTS);

        VBox deleteAccount = new VBox(5);
        deleteAccount.setPadding(new Insets(10));

        Label label = new Label("Load or Remove CourseWindow");
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
                new MyTableColumn("Course Code", "courseCode"),
                new MyTableColumn("Name", "courseName"),
                new MyTableColumn("Description", "description"),
                new MyTableColumn("Credit Hours", "creditHour")
        );

        DataBaseManagement.getInstance().createTable("Course",
                new Column("courseCode", "String", 10),
                new Column("courseName", "String", 15),
                new Column("description", "String", 255),
                new Column("creditHour", "Int", 7)
        );

        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromCourse("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty CourseWindow List");
        }
        window.setCenter(searchResults.getTableView());

    }

    private void onSubmitButtonClicked() {

        DataBaseManagement.getInstance().insertDataIntoTable("Course",
                new ColumnValue(addNew.getTextFieldValue(Constants.COURSE_INPUTS[0]), "courseCode"),
                new ColumnValue(addNew.getTextFieldValue(Constants.COURSE_INPUTS[1]), "courseName"),
                new ColumnValue(addNew.getTextFieldValue(Constants.COURSE_INPUTS[2]), "description"),
                new ColumnValue(addNew.getTextFieldValue(Constants.COURSE_INPUTS[3]), "creditHour")

        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromCourse("*"));
    }

    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("Course",
                "courseCode=\"" + id + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.COURSE_INPUTS[0]), "courseCode"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.COURSE_INPUTS[1]), "courseName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.COURSE_INPUTS[2]), "description"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.COURSE_INPUTS[3]), "creditHour")
        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromCourse("*"));
    }

    private void onLoadButtonClicked() {

        ObservableList<Course> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(course -> {
            editExisting.setTextFieldValue(Constants.COURSE_INPUTS[0], course.getCourseCode());
            editExisting.setTextFieldValue(Constants.COURSE_INPUTS[1], course.getCourseName());
            editExisting.setTextFieldValue(Constants.COURSE_INPUTS[2], course.getDescription());
            editExisting.setTextFieldValue(Constants.COURSE_INPUTS[3], Integer.toString(course.getCreditHour()));
            id = editExisting.getTextFieldValue(Constants.COURSE_INPUTS[0]);

        });
    }

    private void onDeleteButtonClicked() {

        ObservableList<Course> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("Course",
                "courseCode=\"" + account.getCourseCode() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromCourse("*"));
    }


}
