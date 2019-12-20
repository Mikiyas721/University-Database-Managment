package ui.pages;

import database.Column;
import database.ColumnValue;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.UserName;
import ui.customWidget.MyAdminButton;

public class AdminPage {
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField userNameField;
    private PasswordField passWordField;
    private Label messageLabel;

    AdminPage() {
        Application adminPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                Button registrar = new MyAdminButton("Add Registrar").getButton();
                Button teacher = new MyAdminButton("Add Teacher").getButton();
                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);

                VBox adminOperations = new VBox(10, registrar, teacher);
                HBox hBox = new HBox(adminOperations, separator);


                BorderPane window = new BorderPane();
                window.setLeft(hBox);

                registrar.setOnAction(event -> window.setCenter(getAddPage(buttonClicked -> {
                    DataBaseManagement.getInstance().createTable("Registrar",
                            new Column("firstName", "String", 15),
                            new Column("lastName", "String", 15),
                            new Column("email", "String", 15),
                            new Column("username", "String", 15),
                            new Column("password", "String", 15)
                    );
                    if ((firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || userNameField.getText().isEmpty() || passWordField.getText().isEmpty())) {
                        messageLabel.setText("Please fill in all fields");
                    } else if (!UserName.validateEmail(emailField.getText())) {
                        messageLabel.setText("Invalid Email");
                    } else if (!UserName.validatePassword(passWordField.getText())) {
                        messageLabel.setText("Invalid Password. Your password needs to be longer than 7 characters and contain at least one letter(upper and lowercase) and number");
                    } else {
                        messageLabel.setText("");
                        DataBaseManagement.getInstance().insertDataIntoTable("Registrar",
                                new ColumnValue(/*UserName.correctName(*/firstNameField.getText(), "firstName"),
                                new ColumnValue(/*UserName.correctName(*/lastNameField.getText(), "lastName"),
                                new ColumnValue(emailField.getText(), "email"),
                                new ColumnValue(userNameField.getText(), "username"),
                                new ColumnValue(passWordField.getText(), "password"));
                    }

                })));

                teacher.setOnAction(event -> window.setCenter(new Label("Teacher Page")));

                Scene scene = new Scene(window, 500, 500);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
        try {
            adminPage.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GridPane getAddPage(EventHandler<ActionEvent> onSubmitClicked) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 10, 10, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setMaxWidth(350);

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


        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submitButton");
        submitButton.getStylesheets().add("./ui/css/label.css");
        submitButton.setOnAction(onSubmitClicked);
        submitButton.setAlignment(Pos.BASELINE_RIGHT);

        GridPane.setConstraints(firstNameField, 0, 0);
        GridPane.setConstraints(firstNameField, 1, 0);
        GridPane.setConstraints(lastNameLabel, 0, 1);
        GridPane.setConstraints(lastNameField, 1, 1);
        GridPane.setConstraints(emailLabel, 0, 2);
        GridPane.setConstraints(emailField, 1, 2);
        GridPane.setConstraints(userNameLabel, 0, 3);
        GridPane.setConstraints(userNameField, 1, 3);
        GridPane.setConstraints(passwordLabel, 0, 4);
        GridPane.setConstraints(passWordField, 1, 4);
        GridPane.setConstraints(submitButton, 1, 5);
        GridPane.setHalignment(submitButton, HPos.RIGHT);
        GridPane.setConstraints(messageLabel, 0, 6, 2, 2);

        gridPane.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, emailLabel, emailField, userNameLabel, userNameField, passwordLabel, passWordField, submitButton, messageLabel);

        return gridPane;

    }
}
