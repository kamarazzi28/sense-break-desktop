package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sensebreak.gui.Router;

public class VisionController {

    @FXML
    private Button trackingDotBtn;

    @FXML
    private void stratTrackingDot() {
        Stage stage = (Stage) trackingDotBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/TrackingDot.fxml", "Sense Break — Tracking Dot");
    }
}
