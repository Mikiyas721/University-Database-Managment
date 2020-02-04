package ui.pages.customer.admin;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ui.customWidget.ButtonList;
import ui.customWidget.MyButton;
import ui.customWidget.PasswordChangeDialog;

public class SchoolAdminPage {

    private Application SchoolAdminPage;
    private Stage parentStage;

    public SchoolAdminPage(Stage parentStage) {
        this.parentStage = parentStage;
        SchoolAdminPage = new Application() {
            @Override
            public void start(Stage primaryStage) throws Exception {
                BorderPane window = new BorderPane();
                window.getStyleClass().add("mainBlack");
                window.getStylesheets().add("./ui/css/label.css");
                ToolBar toolBar = new ToolBar();
                new SchedulePage(window, toolBar);

                MyButton Schedule = new MyButton("Schedule's", event -> {
                    new SchedulePage(window, toolBar);

                });
                MyButton Finance = new MyButton("Finance", event -> {
                    new FinancePage(window, toolBar);
                });

                //});
                ImageView imageView = new ImageView(getClass().getResource("/assets/back_arrow.png").toExternalForm());
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                imageView.setOnMouseClicked(event -> {
                    primaryStage.close();
                    parentStage.show();
                });
                ButtonList buttonList = new ButtonList(Schedule,Finance);
                Pane spaceHolder = new Pane();
                HBox.setHgrow(spaceHolder, Priority.ALWAYS);
                Button changePassword = new Button("Change Password");
                changePassword.setId("transparentButton");
                changePassword.getStyleClass().add("./ui/css/label.css");
                changePassword.setOnAction(event -> {
                    new PasswordChangeDialog();
                });

                toolBar.getItems().addAll(imageView, buttonList.getHBox(),spaceHolder,changePassword);


                Rectangle2D screen = Screen.getPrimary().getBounds();
                Scene newScene = new Scene(window, screen.getWidth(), screen.getHeight());
                primaryStage.setScene(newScene);
                primaryStage.setTitle("Search");
                primaryStage.show();
            }

        };
        try {
            SchoolAdminPage.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
