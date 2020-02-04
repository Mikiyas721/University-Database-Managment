package ui.pages;

import assistingclasses.Column;
import assistingclasses.ColumnValue;
import database.DataBaseManagement;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.Account.Account;
import models.Account.RegistrarAccount;
import models.Account.StudentAccount;
import models.IT;
import ui.customWidget.LoginDialog;
import ui.pages.customer.Student.StudentPage;
import ui.pages.admin.AdminPage;
import ui.pages.customer.admin.SchoolAdminPage;
import ui.pages.customer.registrar.RegistrarPage;
import ui.pages.customer.teacher.TeacherPage;


public class MainPage extends Application {
    //   Image image = new Image(getClass().getResource("class.jpeg").toExternalForm());

    @Override
    public void start(Stage primaryStage) throws Exception {
        DataBaseManagement.getInstance().openDataBase();
        DataBaseManagement.getInstance().createTable("It",
                new Column("firstName", "String", 10),
                new Column("lastName", "String", 10),
                new Column("email", "String", 10),
                new Column("userName", "String", 10),
                new Column("password", "String", 10)
        );
        if (DataBaseManagement.getInstance().fetchColumnsFromIt("*").size() == 0) {
            DataBaseManagement.getInstance().insertDataIntoTable("It",
                    new ColumnValue("Mr.X", "firstName"),
                    new ColumnValue("Mr.Y", "lastName"),
                    new ColumnValue("xyz156@gmail.com", "email"),
                    new ColumnValue("Abebe159", "userName"),
                    new ColumnValue("Testing123", "password")
            );
        }
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();

        Label openRegistrar = new Label("Registrar");
        openRegistrar.getStylesheets().add("ui/css/label.css");
        openRegistrar.setOnMouseClicked(event -> {
            setOnRegistrarClicked(primaryStage);
        });

        Label openIt = new Label("IT");
        openIt.getStylesheets().add("ui/css/label.css");
        openIt.setOnMouseClicked(event -> {
            setOnAdminClicked(primaryStage);
        });

        Label openStudent = new Label("Student");
        openStudent.getStylesheets().add("ui/css/label.css");
        openStudent.setOnMouseClicked(event -> {
            setOnStudentClicked(primaryStage);
        });

        Label openTeacher = new Label("Teacher");
        openTeacher.getStylesheets().add("ui/css/label.css");
        openTeacher.setOnMouseClicked(event -> {
            new TeacherPage(primaryStage);
            primaryStage.close();
        });
        Label openSchoolAdmin = new Label("Admin");
        openSchoolAdmin.getStylesheets().add("ui/css/label.css");
        openSchoolAdmin.setOnMouseClicked(event -> {
            new SchoolAdminPage(primaryStage);
            primaryStage.close();
        });

        Rectangle2D screen = Screen.getPrimary().getBounds();

        ImageView imageView = new ImageView(getClass().getResource("/assets/campus.jpeg").toExternalForm());
        ImageView imageView1 = new ImageView(getClass().getResource("/assets/campus1.jpeg").toExternalForm());
        ImageView imageView2 = new ImageView(getClass().getResource("/assets/class.jpeg").toExternalForm());
        ImageView imageView3 = new ImageView(getClass().getResource("/assets/AAU-Header_2.jpg").toExternalForm());
        /*imageView.setFitWidth(screen.getWidth());
        imageView.setFitHeight(screen.getHeight()-50);*/
        toolBar.getItems().addAll(openIt, openRegistrar, openStudent, openTeacher, openSchoolAdmin);

        VBox vBox = new VBox(5, toolBar, imageView3);
        borderPane.setTop(vBox);
       /* borderPane.setCenter(imageView);
        borderPane.setLeft(imageView1);
        borderPane.setBottom(imageView2);*/


        Scene scene = new Scene(borderPane, screen.getWidth(), screen.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setOnRegistrarClicked(Stage primaryStage) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setOnLoginClicked(param -> {
            if (param == loginDialog.getLogin()) {
                boolean accountMatches = false;

                if (!loginDialog.getUserNameField().getText().equals("") && !loginDialog.getPassWordField().getText().equals("")) {
                    try {
                        DataBaseManagement.getInstance().openDataBase();
                        ObservableList<RegistrarAccount> userNames = DataBaseManagement.getInstance().fetchColumnsFromRegistrarAccount("*");
                        for (Account accounts : userNames) {
                            if (accounts.getUserName().equals(loginDialog.getUserNameField().getText()) && accounts.getPassword().equals(loginDialog.getPassWordField().getText())) {
                                accountMatches = true;
                                break;
                            }
                        }
                        if (accountMatches) {
                            new RegistrarPage(primaryStage);
                            loginDialog.getLoginDialog().close();
                            primaryStage.close();
                        } else {
                            //TODO add error message different cases
                            loginDialog.setMessage("Please re enter your account information or ask your admin to give you one");
                        }
                    } catch (Exception exception) {
                        loginDialog.setMessage("Please Ask your Admin to register you first");
                    }
                } else {
                    loginDialog.setMessage("Please fill both the fields");
                    System.out.println("dfghbnm");
                }
            }
            return null;
        });
    }

    private void setOnStudentClicked(Stage primaryStage/*, ObservableList<Account> userNames*/) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setOnLoginClicked(param -> {
            if (param == loginDialog.getLogin()) {
                if (!loginDialog.getUserNameField().getText().equals("") && !loginDialog.getPassWordField().getText().equals("")) {
                    try {
                        DataBaseManagement.getInstance().openDataBase();
                        ObservableList<StudentAccount> userNames = DataBaseManagement.getInstance().fetchColumnsFromStudentAccount("*");
                        for (Account accounts : userNames) {
                            if (accounts.getUserName().equals(loginDialog.getUserNameField().getText()) && accounts.getPassword().equals(loginDialog.getPassWordField().getText())) {
                                new StudentPage(primaryStage, accounts.getUserName());
                                loginDialog.getLoginDialog().close();
                                primaryStage.close();
                            }
                        }
                    } catch (Exception exception) {
                        loginDialog.setMessage("Please Ask your Admin to register you first");
                    }
                } else {
                    loginDialog.setMessage("Please fill both the fields");
                    System.out.println("dfghbnm");
                }
            }
            return null;
        });
    }

    private void setOnAdminClicked(Stage primaryStage) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setOnLoginClicked(param -> {
            if (param == loginDialog.getLogin()) {
                if (!loginDialog.getUserNameField().getText().equals("") && !loginDialog.getPassWordField().getText().equals("")) {
                    try {
                        DataBaseManagement.getInstance().openDataBase();
                        ObservableList<IT> userNames = DataBaseManagement.getInstance().fetchColumnsFromIt("*");
                        for (IT it : userNames) {
                            if (it.getUserName().equals(loginDialog.getUserNameField().getText()) && it.getPassword().equals(loginDialog.getPassWordField().getText())) {
                                new AdminPage(primaryStage);
                                loginDialog.getLoginDialog().close();
                                primaryStage.close();
                            }
                        }
                    } catch (Exception exception) {
                        loginDialog.setMessage("Please Ask your Admin to register you first");
                    }
                } else {
                    loginDialog.setMessage("Please fill both the fields");
                    System.out.println("dfghbnm");
                }
            }
            return null;
        });
    }
}
