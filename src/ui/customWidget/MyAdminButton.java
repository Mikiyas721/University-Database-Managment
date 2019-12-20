package ui.customWidget;

import javafx.scene.control.Button;

public class MyAdminButton {
    public Button getButton() {
        return button;
    }

    private Button button;

    public MyAdminButton(String name) {
        button = new Button(name);
        button.setUnderline(true);
        button.getStyleClass().add("transparentButton");
        button.getStylesheets().add("./ui/css/label.css");
    }
}
