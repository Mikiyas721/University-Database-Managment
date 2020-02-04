package ui.pages.customer.teacher;

import assistingclasses.Constants;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Course;
import models.Schedule;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

public class ScheduleWindow {
    private BorderPane window;
    private MyTableView<Schedule> searchResults;

    ScheduleWindow(BorderPane borderPane, ToolBar toolBar/*,String studentId*/) {
        window = borderPane;
        setWindowTop(toolBar);
        setWindowCenter();
    }

    public void setWindowTop(ToolBar toolBar) {
        SearchTool searchTool = new SearchTool(Constants.COURSE_INPUTS);
        searchTool.setOnSearch((observable, oldValue, newValue) -> {
            //searchResults.setItem(DataBaseManagement.getInstance().fetchCourseWithCondition(Constants.getComparingColumn(searchTool.getSelectedRadioButton()), newValue));
        });
        window.setTop(new VBox(toolBar, searchTool.getSearchBar()));

    }

    public void setWindowCenter() {
        searchResults = new MyTableView<>(
                // new MyTableColumn("TeacherId", "teacherId"),
                new MyTableColumn("Course", "courseId"),
                new MyTableColumn("Start Time", "startTime"),
                new MyTableColumn("End Time", "endTime"),
                new MyTableColumn("Day", "dayOfTheWeek"),
                new MyTableColumn("Room", "classRoom"),
                new MyTableColumn("Building Name", "buildingName"),
                new MyTableColumn("Building Number", "buildingNumber")
        );

        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromScheduleTable("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty ScheduleWindow List");
        }
        window.setCenter(searchResults.getTableView());
    }
}
