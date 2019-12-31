package ui.customWidget;

import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class Inputs {

    private GridPane gridPane;

    public Inputs(String sectionWork,
                  String buttonLabel,
                  EventHandler<ActionEvent> onSubmitClicked,
                  String... inputs) {
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
            textField.setPromptText(inputs[i]);
            textField.setId(inputs[i]);
            gridPane.getChildren().add(textField);
            GridPane.setConstraints(textField, j, i + 1);
        }

        Button submitButton = new Button(buttonLabel);
        submitButton.setId("submitButton");
        submitButton.getStylesheets().add("./ui/css/label.css");
        submitButton.setOnAction(onSubmitClicked);
        submitButton.setAlignment(Pos.BASELINE_RIGHT);

        Button clearButton = new Button("Clear");
        clearButton.setId("clearButton");
        clearButton.getStylesheets().add("./ui/css/label.css");
        clearButton.setOnAction(event -> {
            ObservableList<Node> list = gridPane.getChildren();
            for (Node node : list) {
                if (node instanceof TextField) {
                    ((TextField) node).setText("");
                }
            }
        });

        HBox hBox = new HBox(5, clearButton, submitButton);

        gridPane.getChildren().add(hBox);
        GridPane.setConstraints(hBox, 1, inputs.length + 1, 2, 1);
        GridPane.setHalignment(hBox, HPos.RIGHT);

        Label messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.setWrapText(true);
        messageLabel.getStylesheets().add("./ui/css/label.css");

        gridPane.getChildren().add(messageLabel);
        GridPane.setConstraints(messageLabel, 0, inputs.length + 2, 2, 1);
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
}

