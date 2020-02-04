package ui.pages.customer.teacher;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ui.customWidget.ButtonList;
import ui.customWidget.MyButton;


public class TeacherPage {
    private Application teacherPage;

    public TeacherPage(Stage parentStage) {
        teacherPage = new Application() {

            @Override
            public void start(Stage primaryStage) throws Exception {
                BorderPane window = new BorderPane();
                window.getStyleClass().add("mainWhite");
                window.getStylesheets().add("./ui/css/label.css");
                ToolBar toolBar = new ToolBar();
                new CourseWindow(window, toolBar);

                MyButton courseSection = new MyButton("Course", event -> {
                    new CourseWindow(window, toolBar);
                });
                MyButton gradeSection = new MyButton("Grade", event -> {
                    new GradeWindow(window, toolBar);
                });
                MyButton scheduleSection = new MyButton("Schedule", event -> {
                    window.setCenter(new Label("StudentWindow Page"));
                    window.setRight(null);
                    new ScheduleWindow(window, toolBar);
                });
                ImageView imageView = new ImageView(getClass().getResource("/assets/back_arrow.png").toExternalForm());
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                imageView.setOnMouseClicked(event -> {
                    primaryStage.close();
                    parentStage.show();
                });
                ButtonList buttonList = new ButtonList(courseSection, gradeSection, scheduleSection);
                toolBar.getItems().addAll(imageView,buttonList.getHBox());

                Rectangle2D screen = Screen.getPrimary().getBounds();
                Scene newScene = new Scene(window, screen.getWidth(), screen.getHeight());
                primaryStage.setScene(newScene);
                primaryStage.setTitle("Search");
                primaryStage.show();
            }
        };
        try {
            teacherPage.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

