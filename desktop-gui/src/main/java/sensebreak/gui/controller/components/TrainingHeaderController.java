package sensebreak.gui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

public class TrainingHeaderController {

    @FXML
    private Button closeButton;

    @FXML
    private Label timerLabel;

    @FXML
    private AnchorPane root;

    private Runnable onCloseConfirmed;

    @FXML
    public void initialize() {
        root.getProperties().put("controller", this);

        closeButton.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("End Training?");
            confirm.setHeaderText("Are you sure you want to end this training?");
            confirm.setContentText("Your progress will not be saved.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (onCloseConfirmed != null) onCloseConfirmed.run();
            }
        });
    }

    public void setOnCloseConfirmed(Runnable handler) {
        this.onCloseConfirmed = handler;
    }

    public void updateTimerLabel(String text) {
        timerLabel.setText(text);
    }
}
