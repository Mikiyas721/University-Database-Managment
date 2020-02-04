package ui.customWidget;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

public class LoginDialog {

    private Dialog<Pair<String, String>> loginDialog;
    private ButtonType login;
    private TextField userNameField;
    private TextField passWordField;
    private Label messageLabel;

    public LoginDialog() {
        loginDialog = new Dialog<>();
        loginDialog.setTitle("Login");
        loginDialog.setHeaderText("Login into your account");

        //loginDialog.setGraphic();

        messageLabel = new Label();
        messageLabel.setId("messageLabel");
        messageLabel.getStylesheets().add("ui/css/label.css");
        messageLabel.setMinWidth(300);

        Label userName = new Label("User Name");
        userNameField = new TextField();
        userNameField.setPromptText("User Name");

        Label passWord = new Label("Password");
        passWordField = new PasswordField();
        passWordField.setPromptText("PassWord");

        login = new ButtonType("Log In", ButtonBar.ButtonData.OK_DONE);

        loginDialog.getDialogPane().getButtonTypes().addAll(login, ButtonType.CANCEL);


        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30, 10, 10, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        GridPane.setConstraints(userName, 0, 0);
        GridPane.setConstraints(userNameField, 1, 0);
        GridPane.setConstraints(passWord, 0, 1);
        GridPane.setConstraints(passWordField, 1, 1);

        GridPane.setConstraints(messageLabel, 0, 2, 2, 2);

        gridPane.getChildren().addAll(userName, userNameField, passWord, passWordField, messageLabel);

        loginDialog.getDialogPane().setContent(gridPane);
        loginDialog.show();
    }

    public void setOnLoginClicked(Callback<ButtonType, Pair<String,String>> onLoginClicked){
        loginDialog.setResultConverter(onLoginClicked);
    }
    public ButtonType getLogin() {
        return login;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public TextField getPassWordField() {
        return passWordField;
    }
    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setMessage(String messageLabel) {
        this.messageLabel.setText(messageLabel);
    }
    public Dialog<Pair<String, String>> getLoginDialog() {
        return loginDialog;
    }
}
