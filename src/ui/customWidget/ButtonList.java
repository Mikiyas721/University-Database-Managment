package ui.customWidget;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ButtonList {
    public HBox gethBox() {
        return hBox;
    }

    private HBox hBox;

    public ButtonList(MyAdminButton... buttons) {
        hBox = new HBox();
        VBox vBox = new VBox(10);
        vBox.setMinWidth(100);
        vBox.setPadding(new Insets(10,0,0,10));

        for (MyAdminButton myButton : buttons) {
            vBox.getChildren().add(myButton.getButton());
        }
        hBox.getChildren().addAll(vBox, new Separator(Orientation.VERTICAL));
    }
}
