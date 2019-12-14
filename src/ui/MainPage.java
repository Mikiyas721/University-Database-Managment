package ui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class MainPage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();

        Label openRegistrar = new Label("Registrar");
        openRegistrar.getStylesheets().add("ui/css/label.css");
        openRegistrar.setOnMouseClicked(event -> {
            new LogIn();
            primaryStage.close();
        });

        Label openAdmin = new Label("Admin");
        openAdmin.getStylesheets().add("ui/css/label.css");
        openAdmin.setOnMouseClicked(event -> {
            primaryStage.close();
        });

        toolBar.getItems().addAll(openAdmin, openRegistrar);
        borderPane.setTop(toolBar);


        Rectangle2D screen = Screen.getPrimary().getBounds();
        Scene scene = new Scene(borderPane, screen.getWidth(), screen.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
