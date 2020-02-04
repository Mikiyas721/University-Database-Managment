package ui.pages.customer.teacher;

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
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

public class CourseWindow {
    private BorderPane window;
    private MyTableView<models.Course> searchResults;

    CourseWindow(BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        setWindowTop(toolBar);
        setWindowCenter();
    }

    public void setWindowTop(ToolBar toolBar) {
        SearchTool searchTool = new SearchTool(Constants.COURSE_INPUTS);
        searchTool.setOnSearch((observable, oldValue, newValue) -> {
            searchResults.setItem(DataBaseManagement.getInstance().fetchCourseWithCondition(Constants.getComparingColumn(searchTool.getSelectedRadioButton()), newValue));
        });
        window.setTop(new VBox(toolBar, searchTool.getSearchBar()));
    }

    public void setWindowCenter() {
        searchResults = new MyTableView<>(
                new MyTableColumn("Course Number", "courseCode"),
                new MyTableColumn("Course Name", "courseName"),
                new MyTableColumn("description", "description")
        );
        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromCourse("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty StudentWindow List");
        }
        window.setCenter(searchResults.getTableView());
    }

}