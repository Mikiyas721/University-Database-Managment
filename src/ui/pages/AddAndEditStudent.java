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

public class AddAndEditStudent {
    private Application addWindow;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    private UpdateListener updateListener;
    private MyTextField firstName;
    private MyTextField lastName;
    private MyTextField iD;
    private MyTextField sex;
    private MyTextField year;
    private MyTextField phoneNumber;
    private MyTextField dob;
    private MyTextField address;

    AddAndEditStudent(PageType buttonType) {

        addWindow = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                firstName = new MyTextField("First Name");
                lastName = new MyTextField("Last Name");
                iD = new MyTextField("ID");
                sex = new MyTextField("Sex");
                year = new MyTextField("Year");
                phoneNumber = new MyTextField("Phone");
                dob = new MyTextField("DOB");
                address = new MyTextField("Address");
                Button addButton;
                if (buttonType == PageType.ADD) {
                    addButton = new Button("Name");
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
                } else {
                    addButton = new Button("Edit");
                    addButton.setOnAction(event -> {
                        DataBaseManagement.getInstance().updateValueInTable("Student",
                                "id=\"" + getMyTextFieldValue(getID()) + "\"",
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
                }


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


    MyTextField getFirstName() {
        return firstName;
    }

    MyTextField getLastName() {
        return lastName;
    }

    MyTextField getID() {
        return iD;
    }

    MyTextField getSex() {
        return sex;
    }

    MyTextField getYear() {
        return year;
    }

    MyTextField getPhoneNumber() {
        return phoneNumber;
    }

    MyTextField getDob() {
        return dob;
    }

    MyTextField getAddress() {
        return address;
    }

    void setMyTextFieldValue(MyTextField myTextField, String newValue) {
        myTextField.getTextField().setText(newValue);
    }

    String getMyTextFieldValue(MyTextField myTextField) {
        return myTextField.getTextField().getText();
    }

    public void validateInputs() {
        //TODO Add Validation for each input
    }
}

