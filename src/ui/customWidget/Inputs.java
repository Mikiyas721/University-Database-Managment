package ui.customWidget;

import assistingclasses.Constants;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class Inputs {

    private GridPane gridPane;
    private ComboBox<String> colleges;
    private ComboBox<String> department;
    private ComboBox<String> program;
    private ComboBox<Integer> year;
    private ComboBox<String> section;


    public Inputs(String sectionWork,
                  EventHandler<ActionEvent> onSubmitClicked,
                  String buttonLabel,
                  String... inputs) {
        setUpTextFields(sectionWork, inputs);
        setUpActionsAndCombo(buttonLabel, onSubmitClicked, inputs.length);
    }

    //Notice that the order of inputs is how the constructors are overloaded

    public Inputs(String sectionWork,
                  String buttonLabel,
                  EventHandler<ActionEvent> onSubmitClicked,
                  String... inputs) {
        setUpTextFields(sectionWork, inputs);
        setUpActionButtons(buttonLabel, onSubmitClicked, inputs.length);

    }

    private void setUpActionButtons(String buttonLabel, EventHandler<ActionEvent> onSubmitClicked, int length) {
        Button submitButton = new Button(buttonLabel);
        submitButton.setId("submitButton");
        submitButton.getStylesheets().add("./ui/css/label.css");
        submitButton.setOnAction(onSubmitClicked);
        submitButton.setAlignment(Pos.BASELINE_RIGHT);

        Button clearButton = new Button("Clear");
        clearButton.setId("clearButton");
        clearButton.getStylesheets().add("./ui/css/label.css");
        clearButton.setOnAction(event -> {
            clearFields();
        });

        HBox hBox = new HBox(5, clearButton, submitButton);

        gridPane.getChildren().add(hBox);
        GridPane.setConstraints(hBox, 1, length + 1, 2, 1);
        GridPane.setHalignment(hBox, HPos.RIGHT);

        Label messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.setWrapText(true);
        messageLabel.getStylesheets().add("./ui/css/label.css");

        gridPane.getChildren().add(messageLabel);
        GridPane.setConstraints(messageLabel, 0, length + 2, 2, 1);
    }

    private void clearFields() {
        ObservableList<Node> list = gridPane.getChildren();
        for (Node node : list) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
        }
    }

    private void setUpActionsAndCombo(String buttonLabel,
                                      EventHandler<ActionEvent> onSubmitClicked,
                                      int length) {
        Button submitButton = new Button(buttonLabel);
        submitButton.setId("submitButton");
        submitButton.getStylesheets().add("./ui/css/label.css");
        submitButton.setOnAction(onSubmitClicked);
        submitButton.setAlignment(Pos.BASELINE_RIGHT);

        Button clearButton = new Button("Clear");
        clearButton.setId("clearButton");
        clearButton.getStylesheets().add("./ui/css/label.css");
        clearButton.setOnAction(event -> {
            clearFields();
            colleges.setValue(null);
            department.setValue(null);
            program.setValue(null);
            year.setValue(null);
            section.setValue(null);
            department.setDisable(true);
            program.setDisable(true);
            year.setDisable(true);
            section.setDisable(true);
        });

        colleges = new ComboBox<>();
        colleges.setItems(FXCollections.observableArrayList(Constants.colleges));
        colleges.setPromptText("College");

        department = new ComboBox<>();
        department.setPromptText("Department");
        department.setDisable(true);

        program = new ComboBox<>();
        program.setPromptText("Program");
        program.setDisable(true);

        year = new ComboBox<>();
        year.setPromptText("Year");
        year.setDisable(true);

        section = new ComboBox<>();
        section.setPromptText("Section");
        section.setDisable(true);

        HBox hBox = new HBox(5, clearButton, submitButton);
        GridPane.setConstraints(colleges, 1, length + 1, 2, 1);
        GridPane.setConstraints(department, 1, length + 2, 2, 1);
        GridPane.setConstraints(program, 1, length + 3, 2, 1);
        GridPane.setConstraints(year, 1, length + 4, 2, 1);
        GridPane.setConstraints(section, 1, length + 5, 2, 1);

        Label messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.setWrapText(true);
        messageLabel.getStylesheets().add("./ui/css/label.css");

        GridPane.setConstraints(hBox, 1, length + 6, 2, 1);
        GridPane.setHalignment(hBox, HPos.RIGHT);
        GridPane.setConstraints(messageLabel, 0, length + 7, 2, 1);
        gridPane.getChildren().addAll(colleges, department, program, year, section,hBox, messageLabel);
    }

    private void setUpTextFields(String sectionWork, String... inputs) {
        gridPane = new GridPane();
        gridPane.getStyleClass().add("mainBlack");
        gridPane.getStylesheets().add("./ui/css/label.css");
        gridPane.setPadding(new Insets(30, 10, 10, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label sectionWorkLabel = new Label(sectionWork);
        sectionWorkLabel.setId("sectionWork");
        sectionWorkLabel.getStyleClass().add("title");
        sectionWorkLabel.getStylesheets().add("./ui/css/label.css");

        gridPane.getChildren().add(sectionWorkLabel);
        GridPane.setConstraints(sectionWorkLabel, 0, 0, 2, 1);

        for (int i = 0; i < inputs.length; i++) {
            int j = 0;
            Label newLabel = new Label(inputs[i]);
            newLabel.getStyleClass().add("inputLabel");
            newLabel.getStylesheets().add("./ui/css/label.css");
            newLabel.setId(inputs[i] + "Label");
            gridPane.getChildren().add(newLabel);
            GridPane.setConstraints(newLabel, j, i + 1);
            j++;
            TextField textField = new TextField();
            textField.getStyleClass().add("inputField");
            textField.getStylesheets().add("./ui/css/label.css");
            textField.setPromptText(getHintText(inputs[i]));
            textField.setId(inputs[i]);
            gridPane.getChildren().add(textField);
            GridPane.setConstraints(textField, j, i + 1);
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public String getTextFieldValue(String textFieldId) {
        ObservableList<Node> list = gridPane.getChildren();
        for (Node node : list) {
            if (node instanceof TextField && node.getId().equals(textFieldId)) {
                return ((TextField) node).getText();
            }
        }
        return null;
    }

    public void setTextFieldValue(String textFieldId, String newValue) {
        ObservableList<Node> list = gridPane.getChildren();
        for (Node node : list) {
            if (node instanceof TextField && node.getId().equals(textFieldId)) {
                ((TextField) node).setText(newValue);
                break;
            }
        }
    }

    public void setMessage(String message) {
        ObservableList<Node> list = gridPane.getChildren();
        for (Node node : list) {
            if (node instanceof Label && node.getId().equals("messageLabel")) {
                ((Label) node).setText(message);
            }
        }
    }

    private String getHintText(String label) {
        switch (label) {
            case "First Name":
                return "Abebe";
            case "Last Name":
                return "Kebede";
            case "ID":
                return "ATR/8523/09";
            case "Sex":
                return "Female";
            case "DOB":
                return "11/12/1998";
            case "Year":
                return "4";
            case "Phone Number":
                return "963524189";
            case "City":
                return "Addis Ababa";
            case "SubCity":
                return "N/S/L/S/C";
            case "Street":
                return "Menelik";
            case "House No":
                return "1989";
            case "Salary":
                return "1050";
            case "Office Number":
                return "114568974";
            case "Rank":
                return "Kebede";
            case "Email":
                return "Abebe123@gmail.com";
            case "Username":
                return "Abebe159";
            case "Password":
                return "Testing123";
            case "Course Code":
                return "ECEG-1204";
            case "Course Name":
                return "Database Systems";
            case "Credit Hour":
                return "5";
            default:
                return "";
        }
    }

    public ComboBox<String> getDepartment() {
        return department;
    }

    public ComboBox<String> getColleges() {
        return colleges;
    }

    public ComboBox<String> getProgram() {
        return program;
    }

    public ComboBox<Integer> getYear() {
        return year;
    }

    public ComboBox<String> getSection() {
        return section;
    }

}

