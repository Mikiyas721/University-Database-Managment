package ui.pages.admin;

import assistingclasses.*;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
    private RegistrarInputs addNew;
    private RegistrarInputs editExisting;
    private MyTableView<RegistrarAccount> searchResults;
    private String userName = null;

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
                window.setTop(getSearchTools());

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
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));

        }, event -> {
            DataBaseManagement.getInstance().updateValueInTable("RegistrarAccount",
                    "username=\"" + userName + "\"",
                    new ColumnValue<>(editExisting.getFirstName(), "firstName"),
                    new ColumnValue<>(editExisting.getLastName(), "lastName"),
                    new ColumnValue<>(editExisting.getEmail(), "email"),
                    new ColumnValue<>(editExisting.getUserName(), "username"),
                    new ColumnValue<>(editExisting.getPassword(), "password")

            );
            searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));
        }, event -> {
            ObservableList<RegistrarAccount> selected = searchResults.getSelectionModels().getSelectedItems();
            selected.forEach(registrarAccount -> {
                editExisting.setFirstName(registrarAccount.getFirstName());
                editExisting.setLastName(registrarAccount.getLastName());
                editExisting.setEmail(registrarAccount.getEmail());
                editExisting.setUserName(registrarAccount.getUserName());
                editExisting.setPassWord(registrarAccount.getPassword());
                userName = editExisting.getUserName();
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
        addNew = new RegistrarInputs("Submit", "Add New Account", onSubmitClicked);
        editExisting = new RegistrarInputs("Edit", "Edit the Selected Account", onEditClicked);

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

        TableColumn<RegistrarAccount, Integer> noColumn = new TableColumn<>("#");
        noColumn.setMaxWidth(30);
        noColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getTableView().getItems().indexOf(param.getValue()) + 1));

        searchResults.setItem(DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*"));

        return searchResults.getTableView();

    }

    private VBox getSearchTools() {

        GridPane checkBox = new GridPane();
        checkBox.setMaxHeight(50);
        checkBox.setVgap(5);
        checkBox.setHgap(5);
        checkBox.setPadding(new Insets(5));

        CheckBox byFirstName = new CheckBox("First Name");
        byFirstName.setSelected(true);
        /*checkBoxArray[0] = byFirstName;*/

        CheckBox byLastName = new CheckBox("Last Name");
        /* checkBoxArray[1] = byLastName;*/

        CheckBox byId = new CheckBox("Id");
        /*checkBoxArray[2] = byId;*/

        CheckBox bySex = new CheckBox("Sex");
        /*checkBoxArray[3] = bySex;*/

        CheckBox byDob = new CheckBox("DOB");
        /* checkBoxArray[4] = byDob;*/

        CheckBox byAddress = new CheckBox("Address");
        /*checkBoxArray[5] = byAddress;*/

        CheckBox byYear = new CheckBox("Year");
        /*  checkBoxArray[6] = byYear;*/

        CheckBox byPhoneNumber = new CheckBox("Phone");
     /*   checkBoxArray[7] = byPhoneNumber;

        initializeCheckBox();*/

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


        HBox searchRow = new HBox();
        searchRow.setSpacing(5);
        searchRow.getChildren().addAll(search, checkBox);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 5, 2, 10));
        vBox.getChildren().addAll(searchRow, new Separator());
        return vBox;
    }
}
