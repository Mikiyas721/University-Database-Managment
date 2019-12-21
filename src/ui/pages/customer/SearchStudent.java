package ui.pages.customer;

import database.ColumnValue;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Sex;
import models.Student;


public class SearchStudent implements UpdateListener {
    private AddAndEditStudent add;
    private Application searchPage;
    private TableView<Student> searchResults;
    private Label message;
    private CheckBox[] checkBoxArray = new CheckBox[8];

    SearchStudent() {
        searchPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                searchResults = new TableView<>();
                GridPane checkBox = new GridPane();
                checkBox.setMaxHeight(50);
                checkBox.setVgap(5);
                checkBox.setHgap(5);
                checkBox.setPadding(new Insets(5));

                CheckBox byFirstName = new CheckBox("First Name");
                byFirstName.setSelected(true);
                checkBoxArray[0] = byFirstName;

                CheckBox byLastName = new CheckBox("Last Name");
                checkBoxArray[1] = byLastName;

                CheckBox byId = new CheckBox("Id");
                checkBoxArray[2] = byId;

                CheckBox bySex = new CheckBox("Sex");
                checkBoxArray[3] = bySex;

                CheckBox byDob = new CheckBox("DOB");
                checkBoxArray[4] = byDob;

                CheckBox byAddress = new CheckBox("Address");
                checkBoxArray[5] = byAddress;

                CheckBox byYear = new CheckBox("Year");
                checkBoxArray[6] = byYear;

                CheckBox byPhoneNumber = new CheckBox("Phone");
                checkBoxArray[7] = byPhoneNumber;

                initializeCheckBox();

                GridPane.setConstraints(byFirstName, 1, 1);
                GridPane.setConstraints(byLastName, 1, 2);
                GridPane.setConstraints(byId, 2, 1);
                GridPane.setConstraints(bySex, 2, 2);
                GridPane.setConstraints(byDob, 3, 1);
                GridPane.setConstraints(byYear, 3, 2);
                GridPane.setConstraints(byPhoneNumber, 4, 1);
                GridPane.setConstraints(byAddress, 4, 2);

                checkBox.getChildren().addAll(byFirstName, byLastName, byId, bySex, byDob, byAddress, byYear, byPhoneNumber);


                TextField search = new TextField();
                search.setMinWidth(400);
                search.setPromptText("Name");
                search.textProperty().addListener((observable, oldValue, newValue) ->
                        searchResults.setItems(DataBaseManagement.getInstance().fetchWithCondition(getComparingColumn(getSelectedCheckBox()), newValue))
                );

                HBox searchRow = new HBox();
                searchRow.setSpacing(5);
                searchRow.getChildren().addAll(search, checkBox);


                TableColumn<Student, Integer> noColumn = new TableColumn<>("#");
                noColumn.setMaxWidth(30);
                noColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

                TableColumn<Student, String> firstNameColumn = new TableColumn<>("First Name");
                firstNameColumn.setMinWidth(150);
                firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

                TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last Name");
                lastNameColumn.setMinWidth(150);
                lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                TableColumn<Student, String> idColumn = new TableColumn<>("ID");
                idColumn.setMaxWidth(150);
                idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Student, Sex> sexColumn = new TableColumn<>("Sex");
                sexColumn.setMaxWidth(60);
                sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));

                TableColumn<Student, String> yearColumn = new TableColumn<>("Year");
                yearColumn.setMaxWidth(50);
                yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

                TableColumn<Student, Integer> phoneNumberColumn = new TableColumn<>("Phone Number");
                phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

                TableColumn<Student, String> dataOfBirthColumn = new TableColumn<>("DOB");
                dataOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dataOfBirth"));

                TableColumn<Student, String> addressColumn = new TableColumn<>("Address");
                addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

                searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
                searchResults.getColumns().addAll(noColumn, firstNameColumn, lastNameColumn, idColumn, sexColumn, yearColumn, phoneNumberColumn, dataOfBirthColumn, addressColumn);

                HBox operations = new HBox();
                Button addStudent = new Button("Add");
                addStudent.setOnAction(event -> showAddStudentDialog(PageType.ADD));
/*
                private int houseNo;
                private String street;
                private String subCity;
                private String city;
                private String departmentId;*/

                Button editStudent = new Button("Edit");
                editStudent.setOnAction(event -> {
                    ObservableList<Student> selected = searchResults.getSelectionModel().getSelectedItems();
                    if (selected.size() == 0) message.setText("Please select a book first");
                    else if (selected.size() > 1) message.setText("You can only update one book at a time");
                    else {
                        selected.forEach(student ->
                                showAddStudentDialog(PageType.EDIT,
                                        new ColumnValue<>(student.getFirstName(), ""),
                                        new ColumnValue<>(student.getLastName(), ""),
                                        new ColumnValue<>(student.getId(), ""),
                                        new ColumnValue<>(student.getSex(), ""),
                                        new ColumnValue<>(student.getDataOfBirth(), ""),
                                        new ColumnValue<>(student.getPhoneNumber(), ""),
//                                        new ColumnValue<>(student.getAddress(), ""),
                                        new ColumnValue<>(student.getHouseNo(), ""),
                                        new ColumnValue<>(student.getStreet(), ""),
                                        new ColumnValue<>(student.getSubCity(), ""),
                                        new ColumnValue<>(student.getCity(), ""),
                                        new ColumnValue<>(student.getYear(), "")
                                )
                        );

                    }
                });
                Button removeStudent = new Button("Remove");
                removeStudent.setOnAction(event -> {
                    ObservableList<Student> selected = searchResults.getSelectionModel().getSelectedItems();
                    selected.forEach(Student ->
                            DataBaseManagement.getInstance().deleteRowFromTable("Student", "id=\"" + Student.getId() + "\"")
                    );
                    searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
                });
                operations.getChildren().addAll(addStudent, editStudent, removeStudent);
                operations.setSpacing(5);
                operations.setPadding(new Insets(10));

                message = new Label();
                message.setId("messageLabel");
                message.getStylesheets().add("./ui/css/label.css");

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(5));
                vBox.getChildren().addAll(searchRow, searchResults, operations, message);


                Scene newScene = new Scene(vBox, 800, 500);
                primaryStage.setScene(newScene);
                primaryStage.setTitle("Search");
                primaryStage.show();
            }

        };
        try {
            searchPage.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* new ColumnValue<>(student.getHouseNo(), ""),
             new ColumnValue<>(student.getStreet(), ""),
             new ColumnValue<>(student.getSubCity(), ""),
             new ColumnValue<>(student.getCity(), ""),*/

    public void showAddStudentDialog(PageType buttonType, ColumnValue... values) {
        add = new AddAndEditStudent(buttonType);
        if (values.length > 0) {
            add.setMyTextFieldValue(add.getFirstName(), values[0].getValue().toString());
            add.setMyTextFieldValue(add.getLastName(), values[1].getValue().toString());
            add.setMyTextFieldValue(add.getID(), values[2].getValue().toString());
            add.setMyTextFieldValue(add.getSex(), values[3].getValue().toString());
            add.setMyTextFieldValue(add.getDob(), values[4].getValue().toString());
            add.setMyTextFieldValue(add.getPhoneNumber(), values[5].getValue().toString());
            add.setMyTextFieldValue(add.getYear(), values[6].getValue().toString());
//            add.setMyTextFieldValue(add.getAddress(), values[6].getValue().toString());
            add.setMyTextFieldValue(add.getHouseNo(), values[7].getValue().toString());
            add.setMyTextFieldValue(add.getStreet(),values[8].getValue().toString());
            add.setMyTextFieldValue(add.getSubCity(), values[9].getValue().toString());
            add.setMyTextFieldValue(add.getCity(), values[10].getValue().toString());

        }
        add.setUpdateListener(this);
    }

    public Application getSearchPage() {
        return searchPage;
    }

    public void setClickedCheckBox(CheckBox selectedCheckBox) {
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
    }

    public int getSelectedCheckBox() {
        for (int i = 0; i < 7; i++) {
            if (checkBoxArray[i].isSelected()) {
                return i;
            }
        }
        return 0;
    }

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

    @Override
    public void updateTable() {
        searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromStudent("*"));
    }
}
