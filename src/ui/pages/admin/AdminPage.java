package ui.pages.admin;

import database.Column;
import database.ColumnValue;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Account.Account;
import models.Account.RegistrarAccount;
import ui.customWidget.MyAdminButton;
import ui.customWidget.RegistrarInputs;

public class AdminPage {
    private BorderPane window;
    private RegistrarInputs addNew;
    private RegistrarInputs editExisting;

    public AdminPage() {
        Application adminPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                Button registrar = new MyAdminButton("RegistrarAccount").getButton();
                Button teacher = new MyAdminButton("Teacher").getButton();
                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);

                VBox adminOperations = new VBox(10, registrar, teacher);
                HBox hBox = new HBox(adminOperations, separator);


                window = new BorderPane();
                window.setLeft(hBox);

                registrar.setOnAction(event -> setAddNew());

                teacher.setOnAction(event -> {
                    window.setCenter(new Label("Teacher Page"));
                    window.setRight(null);
                });

                Rectangle2D screen = Screen.getPrimary().getBounds();
                Scene scene = new Scene(window, screen.getWidth(), screen.getHeight());
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

    private void setAddNew() {

        window.setRight(getManipulationPage(buttonClicked -> {
            DataBaseManagement.getInstance().createTable("RegistrarAccount",
                    new Column("firstName", "String", 15),
                    new Column("lastName", "String", 15),
                    new Column("email", "String", 15),
                    new Column("username", "String", 15),
                    new Column("password", "String", 15)
            );
            if ((addNew.getFirstName().isEmpty() || addNew.getLastName().isEmpty() || addNew.getUserName().isEmpty() || addNew.getPassword().isEmpty())) {
                addNew.setMessage("Please fill in all fields");
            } else if (!Account.validateEmail(addNew.getEmail())) {
                addNew.setMessage("Invalid Email");
            } else if (!Account.validatePassword(addNew.getPassword())) {
                addNew.setMessage("Invalid Password. Your password needs to be longer than 7 characters and contain at least one letter(upper and lowercase) and number");
            } else {
                addNew.setMessage("");
                DataBaseManagement.getInstance().insertDataIntoTable("RegistrarAccount",
                        new ColumnValue(/*Account.correctName(*/addNew.getFirstName(), "firstName"),
                        new ColumnValue(/*Account.correctName(*/addNew.getLastName(), "lastName"),
                        new ColumnValue(addNew.getEmail(), "email"),
                        new ColumnValue(addNew.getUserName(), "username"),
                        new ColumnValue(addNew.getPassword(), "password"));
            }

        }, null));
        window.setCenter(getTableView());
    }

    private VBox getManipulationPage(EventHandler<ActionEvent> onSubmitClicked, EventHandler<ActionEvent> onEditClicked) {
        VBox mainBox = new VBox(5);
        addNew = new RegistrarInputs("Submit", "Add New Account", onSubmitClicked);
        editExisting = new RegistrarInputs("Edit", "Edit the Selected Account", onEditClicked);

        mainBox.getChildren().addAll(addNew.getGridPane(), new Separator(), editExisting.getGridPane(), new Separator());
        return mainBox;

    }

    public TableView getTableView() {
        TableView<RegistrarAccount> searchResults = new TableView<>();

        TableColumn<RegistrarAccount, Integer> noColumn = new TableColumn<>("#");
        noColumn.setMaxWidth(30);
        noColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        TableColumn<RegistrarAccount, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setMinWidth(150);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<RegistrarAccount, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(150);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<RegistrarAccount, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<RegistrarAccount, String> userNameColumn = new TableColumn<>("User Name");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<RegistrarAccount, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        searchResults.setItems(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));
        searchResults.getColumns().addAll(noColumn, firstNameColumn, lastNameColumn, emailColumn, userNameColumn, passwordColumn);


        return searchResults;

    }
}
