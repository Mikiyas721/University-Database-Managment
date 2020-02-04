package ui.pages.customer.admin;

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
import models.Schedule;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.SearchTool;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SchedulePage {


    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<Schedule> searchResults;
    private String teacherId = null;
    private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    SchedulePage(BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        window.setTop(toolBar);
        setWindowCenter();
        setWindowRight();
    }

    private void setWindowRight() {
        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new Schedule item", "Submit", event -> onSubmitButtonClicked(), Constants.SCHEDULE_INPUTS
        );
        editExisting = new Inputs("Edit Schedule info", "Edit", event -> onEditButtonClicked(), Constants.SCHEDULE_INPUTS);

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
                new MyTableColumn("Course", "courseNumber"),
                //new MyTableColumn("sectionCode", "sectionCode"),
                new MyTableColumn("Start Time", "startTime"),
                new MyTableColumn("End Time", "endTime"),
                new MyTableColumn("Day", "dayOfWeek"),
                new MyTableColumn("Room", "classRoom"),
                new MyTableColumn("Bldg Name", "bldgName")
        );

        DataBaseManagement.getInstance().createTable("Schedule",
                new Column("courseNumber", "String", 15),
                // new Column("sectionCode", "String", 15),
                new Column("startTime", "String", 15),
                new Column("endTime", "String", 15),
                new Column("dayOfWeek", "String", 15),
                new Column("classRoom", "String", 15),
                new Column("bldgName", "String", 15)
        );
        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromScheduleTable("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty RegistrarAccount List");
        }
        window.setCenter(searchResults.getTableView());


    }

    public static String getComparingColumn(int i) {
        if (i == 1) return "fee";
        else if (i == 2) return "programid";
        else if (i == 3) return "collegeid";
        else return "programid";

    }


    private String getDayOfTheWeekString() {
        String dayString = addNew.getTextFieldValue(Constants.SCHEDULE_INPUTS[3]).toUpperCase();
        if (dayString.equals(DayOfWeek.MONDAY.toString())) return DayOfWeek.MONDAY.toString();
        else if (dayString.equals(DayOfWeek.TUESDAY.toString())) return DayOfWeek.TUESDAY.toString();
        else if (dayString.equals(DayOfWeek.WEDNESDAY.toString())) return DayOfWeek.WEDNESDAY.toString();
        else if (dayString.equals(DayOfWeek.THURSDAY.toString())) return DayOfWeek.THURSDAY.toString();
        else if (dayString.equals(DayOfWeek.FRIDAY.toString())) return DayOfWeek.FRIDAY.toString();
        else if (dayString.equals(DayOfWeek.SATURDAY.toString())) return DayOfWeek.SATURDAY.toString();
        else if (dayString.equals(DayOfWeek.SUNDAY.toString())) return DayOfWeek.SUNDAY.toString();
        else return null;

    }

    private void onSubmitButtonClicked() {
        String startTimeString = addNew.getTextFieldValue(Constants.SCHEDULE_INPUTS[1]);
        LocalTime startTime = LocalTime.parse(startTimeString);
        String endTimeString = addNew.getTextFieldValue(Constants.SCHEDULE_INPUTS[2]);
        LocalTime endTime = LocalTime.parse(endTimeString);
        String dayString = getDayOfTheWeekString();
        DataBaseManagement.getInstance().insertDataIntoTable("Schedule",
                new ColumnValue(addNew.getTextFieldValue(Constants.SCHEDULE_INPUTS[0]), "courseNumber"),
                new ColumnValue(startTime.format(formatter), "startTime"),
                new ColumnValue(endTime.format(formatter), "endTime"),
                new ColumnValue(dayString, "dayOfWeek"),
                new ColumnValue(addNew.getTextFieldValue(Constants.SCHEDULE_INPUTS[4]), "classRoom"),
                new ColumnValue(addNew.getTextFieldValue(Constants.SCHEDULE_INPUTS[5]), "bldgName")
        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromScheduleTable("*"));
    }


    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("ScheduleTable",
                "teacherId=\"" + teacherId + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHEDULE_INPUTS[0]), "courseNumber"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHEDULE_INPUTS[1]), "startTime"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHEDULE_INPUTS[2]), "endTime"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHEDULE_INPUTS[3]), "dayOfWeek"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHEDULE_INPUTS[4]), "classRoom"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.SCHEDULE_INPUTS[5]), "bldgName")

        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromScheduleTable("*"));
    }


    private void onLoadButtonClicked() {
        ObservableList<Schedule> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(schedule -> {
            editExisting.setTextFieldValue(Constants.SCHEDULE_INPUTS[0], schedule.getCourseNumber());
            editExisting.setTextFieldValue(Constants.SCHEDULE_INPUTS[1], schedule.getStartTime().toString());
            editExisting.setTextFieldValue(Constants.SCHEDULE_INPUTS[2], schedule.getEndTime().toString());
            editExisting.setTextFieldValue(Constants.SCHEDULE_INPUTS[3], schedule.getDayOfWeek().toString());
            editExisting.setTextFieldValue(Constants.SCHEDULE_INPUTS[4], schedule.getClassRoom());
            editExisting.setTextFieldValue(Constants.SCHEDULE_INPUTS[5], schedule.getBldgName());

        });

    }

    private void onDeleteButtonClicked() {
        ObservableList<Schedule> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("Schedule",
                "classRoom=\"" + account.getClassRoom() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromScheduleTable("*"));

    }


}
