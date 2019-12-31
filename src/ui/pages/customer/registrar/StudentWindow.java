package ui.pages.customer.registrar;

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
import models.Program;
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
                Constants.STUDENT_INPUTS[0],
                Constants.STUDENT_INPUTS[1],
                Constants.STUDENT_INPUTS[2],
                Constants.STUDENT_INPUTS[3],
                Constants.STUDENT_INPUTS[4],
                Constants.STUDENT_INPUTS[5],
                Constants.STUDENT_INPUTS[6],
                Constants.STUDENT_INPUTS[7],
                Constants.STUDENT_INPUTS[8],
                Constants.STUDENT_INPUTS[9],
                Constants.STUDENT_INPUTS[10]
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
        addNew = new Inputs("Add new student", "Submit", event -> onSubmitButtonClicked(), Constants.STUDENT_INPUTS
        );
        editExisting = new Inputs("Edit student information", "Edit", event -> onEditButtonClicked(), Constants.STUDENT_INPUTS);

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
        ObservableList<String> collage = FXCollections.observableArrayList();
        collage.addAll("AAIT","CNCS","CBE","CDS","CEBS","CHS","CHLJC","CLGS","CSS","CVMA","CPVA");

        ObservableList<String> department = FXCollections.observableArrayList();
        department.addAll("SECE", "SCEE", "SMIE");

        ObservableList<Program> program = FXCollections.observableArrayList();
        program.addAll(Program.Under_Grad,Program.Grad,Program.Post_Grad);

        ObservableList<Integer> year = FXCollections.observableArrayList();
        year.addAll(1,2,3,4,5);

        ObservableList<String> section = FXCollections.observableArrayList();
        section.addAll("A","B","C","D");

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setMinWidth(100);

        ComboBox<String> collages = new ComboBox<>(collage);
        collages.setPromptText("Collage");

        ComboBox<String> departments = new ComboBox<>(department);
        departments.setPromptText("Department");

        ComboBox<Program> programs = new ComboBox<>(program);
        programs.setPromptText("Program");

        ComboBox<Integer> years = new ComboBox<>(year);
        years.setPromptText("Year");

        ComboBox<String> sections = new ComboBox<>(section);
        sections.setPromptText("Section");


        vBox.getChildren().addAll(collages,departments, programs, years, sections);
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

        DataBaseManagement.getInstance().insertDataIntoTable("Student",
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[0]), "firstName"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[1]), "lastName"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[2]), "id"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[3]), "sex"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[4]), "year"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[5]), "dataOfBirth"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[6]), "phoneNumber"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[7]), "city"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[8]), "subCity"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[9]), "street"),
                new ColumnValue(addNew.getTextFieldValue(Constants.STUDENT_INPUTS[10]), "houseNo")
        );
       searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }

    private void onEditButtonClicked() {
        DataBaseManagement.getInstance().updateValueInTable("Student",
                "id=\"" + id + "\"",
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[0]), "firstName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[1]), "lastName"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[2]), "id"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[3]), "sex"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[4]), "year"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[5]), "dataOfBirth"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[6]), "phoneNumber"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[7]), "city"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[8]), "subCity"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[9]), "street"),
                new ColumnValue<>(editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[10]), "houseNo")

        );
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }

    private void onLoadButtonClicked() {

        ObservableList<models.Student> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(student -> {
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[0], student.getFirstName());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[1], student.getLastName());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[2], student.getId());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[3], student.getSex().toString());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[4], Integer.toString(student.getYear()));
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[5], models.Student.getLocalDateString(student.getDataOfBirth()));
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[6], Integer.toString(student.getPhoneNumber()));
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[7], student.getCity());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[8], student.getSubCity());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[9], student.getStreet());
            editExisting.setTextFieldValue(Constants.STUDENT_INPUTS[10], Integer.toString(student.getHouseNo()));
            id = editExisting.getTextFieldValue(Constants.STUDENT_INPUTS[2]);
        });
    }

    private void onDeleteButtonClicked() {

        ObservableList<models.Student> selected = searchResults.getSelectionModels().getSelectedItems();
        selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("Student",
                "id=\"" + account.getId() + "\""));
        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }


}
