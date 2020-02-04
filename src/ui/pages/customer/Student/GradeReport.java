package ui.pages.customer.Student;

import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import models.Grade;
import ui.customWidget.MyTableView;

class GradeReport {
    private BorderPane window;
    private MyTableView<Grade> searchResults;
    private String studentId;

    GradeReport(BorderPane borderPane, ToolBar toolBar, String studentId) {
        window = borderPane;
        this.studentId = studentId;
        window.setTop(toolBar);
        setWindowCenter();
    }

    private void setWindowCenter() {
        searchResults = new MyTableView<>(
                new MyTableColumn("Course", "courseCode"),
                new MyTableColumn("Grade", "letterGrade"),
                new MyTableColumn("Grade Point", "numericGrade")
        );

        try {
             searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromGrade("*"));
            //searchResults.setItem(DataBaseManagement.getInstance().fetchGradeWithCondition("studentId = '" + studentId + "'"));
        } catch (NullPointerException e) {
            System.out.println("Empty SchoolAdminAccount List");
        }
        window.setCenter(searchResults.getTableView());

    }

}
