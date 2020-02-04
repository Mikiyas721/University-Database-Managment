package ui.pages.customer.Student;

import assistingclasses.Constants;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.Course;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

class CourseList {
    private BorderPane window;
    private MyTableView<Course> searchResults;
    private String studentId;

    CourseList(BorderPane borderPane, ToolBar toolBar, String studentId) {
        this.studentId = studentId;
        window = borderPane;
        window.setTop(toolBar);
        //setWindowTop(toolBar);
        setWindowCenter();
    }

    private void setWindowCenter() {
        searchResults = new MyTableView<>(
                new MyTableColumn("Code", "courseCode"),
                new MyTableColumn("Name", "courseName"),
                new MyTableColumn("Description", "description"),
                new MyTableColumn("Credit Hour", "creditHour")
        );
        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromCourse("*"));
            //searchResults.setItem(DataBaseManagement.getInstance().getCourseForStudent(studentId)); // remember
        } catch (NullPointerException e) {
            System.out.println("Empty RegistrarAccount List");
        }
        window.setCenter(searchResults.getTableView());
    }

}
