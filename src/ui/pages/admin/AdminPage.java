package ui.pages.admin;

import assistingclasses.*;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import models.Account.*;
import ui.customWidget.*;

public class AdminPage {
    private BorderPane window;
    private Inputs addNew;
    private Inputs editExisting;
    private MyTableView<RegistrarAccount> searchResults;
    private String userName = null;

    public AdminPage() {
        Application adminPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                CheckBoxGrid checkBoxGrid = new CheckBoxGrid(
                        Inputs.REGISTRAR_INPUTS[0],
                        Inputs.REGISTRAR_INPUTS[1],
                        Inputs.REGISTRAR_INPUTS[2],
                        Inputs.REGISTRAR_INPUTS[3],
                        Inputs.REGISTRAR_INPUTS[4]
                );
                TextField search = new TextField();
                search.setMinWidth(400);
                search.setPromptText("Name");


                HBox searchRow = new HBox();
                searchRow.setSpacing(5);
                searchRow.getChildren().addAll(search, checkBoxGrid.getCheckBoxGrid());

                VBox searchBar = new VBox();
                searchBar.setPadding(new Insets(10, 5, 2, 10));
                searchBar.getChildren().addAll(searchRow, new Separator());

                window = new BorderPane();
                window.setLeft(new ButtonList(
                                new MyAdminButton("Registrar", event -> setAddNew()),
                                new MyAdminButton("Teacher", event -> {
                                    window.setCenter(new Label("Teacher Page"));
                                    window.setRight(null);
                                }),
                                new MyAdminButton("Student", event -> {
                                    window.setCenter(new Label("Student Page"));
                                    window.setRight(null);
                                })
                        ).gethBox()
                );
                window.setTop(searchBar);

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
            if ((addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[0]).isEmpty() ||
                    addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[1]).isEmpty() ||
                    addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[3]).isEmpty() ||
                    addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[4]).isEmpty())) {
                addNew.setMessage("Please fill in all fields");
            } else if (!Account.validateEmail(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[2]))) {
                addNew.setMessage("Invalid Email");
            } else if (!Account.validatePassword(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[4]))) {
                addNew.setMessage("Invalid Password. Your password needs to be longer than 7 characters and contain at least one letter(upper and lowercase) and number");
            } else {
                addNew.setMessage("");
                DataBaseManagement.getInstance().insertDataIntoTable("RegistrarAccount",
                        new ColumnValue(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[0]), "firstName"),
                        new ColumnValue(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[1]), "lastName"),
                        new ColumnValue(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[2]), "email"),
                        new ColumnValue(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[3]), "username"),
                        new ColumnValue(addNew.getTextFieldValue(Inputs.REGISTRAR_INPUTS[4]), "password"));
            }
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));

        }, event -> {
            DataBaseManagement.getInstance().updateValueInTable("RegistrarAccount",
                    "username=\"" + userName + "\"",
                    new ColumnValue<>(editExisting.getTextFieldValue(Inputs.REGISTRAR_INPUTS[0]), "firstName"),
                    new ColumnValue<>(editExisting.getTextFieldValue(Inputs.REGISTRAR_INPUTS[1]), "lastName"),
                    new ColumnValue<>(editExisting.getTextFieldValue(Inputs.REGISTRAR_INPUTS[2]), "email"),
                    new ColumnValue<>(editExisting.getTextFieldValue(Inputs.REGISTRAR_INPUTS[3]), "username"),
                    new ColumnValue<>(editExisting.getTextFieldValue(Inputs.REGISTRAR_INPUTS[4]), "password")

            );
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));
        }, event -> {
            ObservableList<RegistrarAccount> selected = searchResults.getSelectionModels().getSelectedItems();
            selected.forEach(registrarAccount -> {
                editExisting.setTextFieldValue(Inputs.REGISTRAR_INPUTS[0], registrarAccount.getFirstName());
                editExisting.setTextFieldValue(Inputs.REGISTRAR_INPUTS[1], registrarAccount.getLastName());
                editExisting.setTextFieldValue(Inputs.REGISTRAR_INPUTS[2], registrarAccount.getEmail());
                editExisting.setTextFieldValue(Inputs.REGISTRAR_INPUTS[3], registrarAccount.getUserName());
                editExisting.setTextFieldValue(Inputs.REGISTRAR_INPUTS[4], registrarAccount.getPassword());
                userName = editExisting.getTextFieldValue(Inputs.REGISTRAR_INPUTS[3]);
            });
        }, event -> {
            ObservableList<RegistrarAccount> selected = searchResults.getSelectionModels().getSelectedItems();
            selected.forEach(account -> DataBaseManagement.getInstance().deleteRowFromTable("RegistrarAccount",
                    "username=\"" + account.getUserName() + "\""));
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));
        }));
        window.setCenter(getTableView());
    }

    private ScrollPane getManipulationPage(EventHandler<ActionEvent> onSubmitClicked,
                                           EventHandler<ActionEvent> onEditClicked,
                                           EventHandler<ActionEvent> onLoadClicked,
                                           EventHandler<ActionEvent> onDeleteClicked) {
        ScrollPane scrollPane = new ScrollPane();
        VBox mainBox = new VBox(5);
        addNew = new Inputs("Add new registrar access", "Submit", onSubmitClicked, Inputs.REGISTRAR_INPUTS
        );
        editExisting = new Inputs("Edit the Selected Account", "Edit", onEditClicked, Inputs.REGISTRAR_INPUTS);

        VBox deleteAccount = new VBox(5);
        deleteAccount.setPadding(new Insets(10));

        Label label = new Label("Load or Remove Account");
        label.getStyleClass().add("title");
        label.getStylesheets().add("./ui/css/label.css");

        Button loadButton = new Button("Load");
        loadButton.setId("loadButton");
        loadButton.getStylesheets().add("./ui/css/label.css");
        loadButton.setOnAction(onLoadClicked);

        Button deleteButton = new Button("Delete");
        deleteButton.setId("deleteButton");
        deleteButton.getStylesheets().add("./ui/css/label.css");
        deleteButton.setOnAction(onDeleteClicked);


        deleteAccount.getChildren().addAll(label, loadButton, deleteButton);

        mainBox.getChildren().addAll(addNew.getGridPane(), new Separator(), editExisting.getGridPane(), new Separator(), deleteAccount);
        scrollPane.setContent(mainBox);
        scrollPane.setMinWidth(250);
        return scrollPane;

    }

    private TableView getTableView() {
        searchResults = new MyTableView<>(
                new MyTableColumn("First Name", "firstName"),
                new MyTableColumn("Last Name", "lastName"),
                new MyTableColumn("Email", "email"),
                new MyTableColumn("User Name", "userName"),
                new MyTableColumn("Password", "password")
        );

        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));

        return searchResults.getTableView();

    }

}
