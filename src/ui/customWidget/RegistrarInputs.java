package ui.customWidget;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegistrarInputs {
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField userNameField;
    private PasswordField passWordField;
    private Label messageLabel;
    private GridPane gridPane;

    public GridPane getGridPane() {
        return gridPane;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getUserName() {
        return userNameField.getText();
    }

    public String getPassword() {
        return passWordField.getText();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public RegistrarInputs(String buttonLabel, String title, EventHandler<ActionEvent> onSubmitClicked) {

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 10, 10, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setMaxWidth(350);

        Label sectionWork = new Label(title);
        sectionWork.setId("title");
        sectionWork.getStylesheets().add("./ui/css/label.css");

        Label firstNameLabel = new Label("First Name");
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        Label lastNameLabel = new Label("Last Name");
        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        Label emailLabel = new Label("Email");
        emailField = new TextField();
        emailField.setPromptText("Email");

        Label userNameLabel = new Label("User Name");
        userNameField = new TextField();
        userNameField.setPromptText("User Name");

        Label passwordLabel = new Label("Password");
        passWordField = new PasswordField();
        passWordField.setPromptText("PassWord");

        messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.setWrapText(true);
        messageLabel.getStylesheets().add("./ui/css/label.css");


        Button submitButton = new Button(buttonLabel);
        submitButton.getStyleClass().add("submitButton");
        submitButton.getStylesheets().add("./ui/css/label.css");
        submitButton.setOnAction(onSubmitClicked);
        submitButton.setAlignment(Pos.BASELINE_RIGHT);

        GridPane.setConstraints(sectionWork, 0, 0,2,1);
        GridPane.setConstraints(firstNameLabel, 0, 1);
        GridPane.setConstraints(firstNameField, 1, 1);
        GridPane.setConstraints(lastNameLabel, 0, 2);
        GridPane.setConstraints(lastNameField, 1, 2);
        GridPane.setConstraints(emailLabel, 0, 3);
        GridPane.setConstraints(emailField, 1, 3);
        GridPane.setConstraints(userNameLabel, 0, 4);
        GridPane.setConstraints(userNameField, 1, 4);
        GridPane.setConstraints(passwordLabel, 0, 5);
        GridPane.setConstraints(passWordField, 1, 5);
        GridPane.setConstraints(submitButton, 1, 6);
        GridPane.setHalignment(submitButton, HPos.RIGHT);
        GridPane.setConstraints(messageLabel, 0, 7, 2, 2);

        gridPane.getChildren().addAll(sectionWork, firstNameLabel, firstNameField, lastNameLabel, lastNameField, emailLabel, emailField, userNameLabel, userNameField, passwordLabel, passWordField, submitButton, messageLabel);

    }
}
