package ui.pages.customer.registrar;

import assistingclasses.Column;
import assistingclasses.ColumnValue;
import assistingclasses.MyTableColumn;
import database.DataBaseManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Student;
import ui.customWidget.CheckBoxGrid;
import ui.customWidget.Inputs;
import ui.customWidget.MyTableView;
import ui.customWidget.MyTextField;

public class StudentWindow {
    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<Student> searchResults;
    private String id = null;

    StudentWindow(BorderPane borderPane, ToolBar toolBar) {
        window = borderPane;
        setWindowTop(toolBar);
        setWindowLeft();
        setWindowCenter();
        setWindowRight();
    }

    public void setWindowTop(ToolBar toolBar) {
        ObservableList<String> department = FXCollections.observableArrayList();
        department.addAll("SECE", "SCEE", "SMIE");

        CheckBoxGrid checkBox = new CheckBoxGrid(
                Inputs.STUDENT_INPUTS[0],
                Inputs.STUDENT_INPUTS[1],
                Inputs.STUDENT_INPUTS[2],
                Inputs.STUDENT_INPUTS[3],
                Inputs.STUDENT_INPUTS[4],
                Inputs.STUDENT_INPUTS[5],
                Inputs.STUDENT_INPUTS[6],
                Inputs.STUDENT_INPUTS[7],
                Inputs.STUDENT_INPUTS[8],
                Inputs.STUDENT_INPUTS[9],
                Inputs.STUDENT_INPUTS[10]
        );
        TextField search = new TextField();
        search.setMinWidth(400);
        search.setPromptText("Search");
        search.textProperty().addListener((observable, oldValue, newValue) -> {
                }
                /*searchResults.setItem(DataBaseManagement.getInstance().fetchWithCondition(getComparingColumn(getSelectedCheckBox()), newValue))*/
        );

        HBox searchRow = new HBox();
        searchRow.setSpacing(5);
        searchRow.getChildren().addAll(search, checkBox.getCheckBoxGrid());

        VBox searchBar = new VBox(searchRow, new Separator());
        searchBar.setPadding(new Insets(10, 0, 0, 10));

        window.setTop(new VBox(toolBar, searchBar));

    }

    public void setWindowRight() {

        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new student", "Submit", event -> onSubmitButtonClicked(), Inputs.STUDENT_INPUTS
        );
        editExisting = new Inputs("Edit student information", "Edit", event -> onEditButtonClicked(), Inputs.STUDENT_INPUTS);

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

    public void setWindowLeft() {
        ObservableList<String> department = FXCollections.observableArrayList();
        department.addAll("SECE", "SCEE", "SMIE");
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(100);

        ComboBox<String> comboBox = new ComboBox<>(department);
        ComboBox<String> comboBox2 = new ComboBox<>(department);
        ComboBox<String> comboBox3 = new ComboBox<>(department);
        ComboBox<String> comboBox4 = new ComboBox<>(department);

        vBox.getChildren().addAll(comboBox, new Label("Department"), comboBox2, comboBox3, comboBox4);
        window.setLeft(vBox);
    }

    public void setWindowCenter() {
        searchResults = new MyTableView<>(
                new MyTableColumn("First Name", "firstName"),
                new MyTableColumn("Last Name", "lastName"),
                new MyTableColumn("ID", "id"),
                new MyTableColumn("Sex", "sex"),
                new MyTableColumn("Year", "year"),
                new MyTableColumn("Phone Number", "phoneNumber"),
                new MyTableColumn("DOB", "dataOfBirth"),
                new MyTableColumn("City", "city"),
                new MyTableColumn("SubCity", "subCity"),
                new MyTableColumn("Street", "city"),
                new MyTableColumn("House No", "houseNo")
        );

        DataBaseManagement.getInstance().createTable("Student",
                new Column("firstName", "String", 15),
                new Column("lastName", "String", 15),
                new Column("id", "String", 15),
                new Column("sex", "String", 7),
                new Column("year", "Int", 15),
                new Column("dataOfBirth", "String", 15),
                new Column("phoneNumber", "Int", 15),
                new Column("city", "String", 10),
                new Column("subCity", "String", 10),
                new Column("street", "String", 10),
                new Column("houseNo", "Int", 10)
        );

        try {
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
        } catch (NullPointerException e) {
            System.out.println("Empty StudentWindow List");
        }
        window.setCenter(searchResults.getTableView());

    }

   /* public Application getSearchPage() {
        return searchPage;
    }*/

   /* public void setClickedCheckBox(CheckBox selectedCheckBox) {
        for (CheckBox checkBox : checkBoxArray) {
            if (checkBox == selectedCheckBox) continue;
            checkBox.setSelected(false);
        }
    }

    public void initializeCheckBox() {
        for (CheckBox checkBox : checkBoxArray) {
            checkBox.setOnMouseClicked(event -> {
                setClickedCheckBox(checkBox);
            });
        }
    }*/

   /* public int getSelectedCheckBox() {
        for (int i = 0; i < 7; i++) {
            if (checkBoxArray[i].isSelected()) {
                return i;
            }
        }
        return 0;
    }*/

    public String getComparingColumn(int i) {
        if (i == 1) return "lastName";
        else if (i == 2) return "id";
        else if (i == 3) return "sex";
        else if (i == 4) return "dob";
        else if (i == 5) return "address";
        else if (i == 6) return "year";
        else if (i == 7) return "phoneNumber";
        else return "firstName";
    }

    private void onSubmitButtonClicked() {

        DataBaseManagement.getInstance().insertDataIntoTable("StudentWindow",
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[0]), "firstName"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[1]), "lastName"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[2]), "id"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[3]), "sex"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[4]), "year"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[5]), "dataOfBirth"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[6]), "phoneNumber"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[7]), "city"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[8]), "subCity"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[9]), "street"),
                new ColumnValue(addNew.getTextFieldValue(Inputs.STUDENT_INPUTS[10]), "houseNo")
        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }

    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("StudentWindow",
                "id=\"" + id + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[0]), "firstName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[1]), "lastName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[2]), "id"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[3]), "sex"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[4]), "year"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[5]), "dataOfBirth"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[6]), "phoneNumber"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[7]), "city"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[8]), "subCity"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[9]), "street"),
                new ColumnValue<>(editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[10]), "houseNo")

        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }

    private void onLoadButtonClicked() {

        ObservableList<models.Student> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(student -> {
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[0], student.getFirstName());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[1], student.getLastName());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[2], student.getId());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[3], student.getSex().toString());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[4], Integer.toString(student.getYear()));
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[5], models.Student.getLocalDateString(student.getDataOfBirth()));
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[6], Integer.toString(student.getPhoneNumber()));
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[7], student.getCity());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[8], student.getSubCity());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[9], student.getStreet());
            editExisting.setTextFieldValue(Inputs.STUDENT_INPUTS[10], Integer.toString(student.getHouseNo()));
            id = editExisting.getTextFieldValue(Inputs.STUDENT_INPUTS[2]);
        });
    }

    private void onDeleteButtonClicked() {

        ObservableList<models.Student> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("StudentWindow",
                "id=\"" + account.getId() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }


}
