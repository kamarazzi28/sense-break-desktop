package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sensebreak.gui.Router;

/**
 * Controller for the Vision training screen.
 * Handles user interaction for launching the Tracking Dot exercise.
 */
public class VisionController {

    @FXML
    private Button trackingDotBtn;

    /**
     * Handles the action to start the Tracking Dot training.
     * Switches the scene to the Tracking Dot screen.
     */
    @FXML
    private void stratTrackingDot() {
        Stage stage = (Stage) trackingDotBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/TrackingDot.fxml", "Sense Break — Tracking Dot");
    }
}
