package ui.pages;

import database.ColumnValue;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.customWidget.MyTextField;

public class AddStudent {
    private Application addWindow;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    private UpdateListener updateListener;


    AddStudent() {

        addWindow = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                MyTextField firstName = new MyTextField("First Name");
                MyTextField lastName = new MyTextField("Last Name");
                MyTextField iD = new MyTextField("ID");
                MyTextField sex = new MyTextField("Sex");
                MyTextField year = new MyTextField("Year");
                MyTextField phoneNumber = new MyTextField("Phone");
                MyTextField dob = new MyTextField("DOB");
                MyTextField address = new MyTextField("Address");

                Button addButton = new Button("Add");
                addButton.setOnAction(event -> {
                    DataBaseManagement.getInstance().insertDataIntoTable("Student",
                            new ColumnValue<>(firstName.getTextField().getText(), "firstName"),
                            new ColumnValue<>(lastName.getTextField().getText(), "lastName"),
                            new ColumnValue<>(iD.getTextField().getText(), "id"),
                            new ColumnValue<>(sex.getTextField().getText(), "sex"),
                            new ColumnValue<>(year.getTextField().getText(), "year"),
                            new ColumnValue<>(dob.getTextField().getText(), "dob"),
                            new ColumnValue<>(address.getTextField().getText(), "address"),
                            new ColumnValue<>(phoneNumber.getTextField().getText(), "phoneNumber")

                    );
                    updateListener.updateTable();
                });

                Button cancelButton = new Button("Clear");
                cancelButton.setOnAction(e -> {
                    firstName.getTextField().clear();
                    lastName.getTextField().clear();
                    iD.getTextField().clear();
                    sex.getTextField().clear();
                    year.getTextField().clear();
                    phoneNumber.getTextField().clear();
                    dob.getTextField().clear();
                    address.getTextField().clear();
                });
                HBox operationButtons = new HBox();
                operationButtons.setPadding(new Insets(20, 20, 20, 120));
                operationButtons.setSpacing(20);
                operationButtons.getChildren().addAll(cancelButton, addButton);

                Label message = new Label();

                VBox vBox = new VBox();
                vBox.setPadding(new Insets(20, 20, 20, 20));
                vBox.getChildren().addAll(
                        firstName.gethBox(),
                        lastName.gethBox(),
                        iD.gethBox(),
                        sex.gethBox(),
                        year.gethBox(),
                        phoneNumber.gethBox(),
                        dob.gethBox(),
                        address.gethBox(),
                        operationButtons,
                        message);

                Scene scene = new Scene(vBox, 500, 600);

                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
        try {
            addWindow.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateInputs() {
        //TODO Add Validation for each input
    }
}

