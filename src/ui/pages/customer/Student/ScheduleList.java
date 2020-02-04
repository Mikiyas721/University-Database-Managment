package ui.pages.customer.Student;

import assistingclasses.Constants;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.Schedule;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

public class ScheduleList {
    private BorderPane window;
    private MyTableView<Schedule> searchResults;
    private String studentId;

    ScheduleList(BorderPane borderPane, ToolBar toolBar, String studentId) {
        this.studentId = studentId;
        window = borderPane;
        window.setTop(toolBar);
        setWindowCenter();
    }

    private void setWindowCenter() {
        searchResults = new MyTableView<>(
                new MyTableColumn("Course", "courseCode"),
                new MyTableColumn("Start Time", "startTime"),
                new MyTableColumn("End Time", "endTime"),
                new MyTableColumn("Day", "dayOfWeek"),
                new MyTableColumn("Room", "classRoom"),
                new MyTableColumn("Bldg Name", "bldgName")
        );

        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromScheduleTable("*")); // remember
        } catch (NullPointerException e) {
            System.out.println("Empty SchoolAdminAccount List");
        }
        window.setCenter(searchResults.getTableView());
    }
}
