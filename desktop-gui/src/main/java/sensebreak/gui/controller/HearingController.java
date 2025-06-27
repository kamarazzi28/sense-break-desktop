package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sensebreak.gui.Router;
import javafx.scene.control.Button;

import java.io.InputStream;

public class HearingController {
    @FXML
    private ImageView girlImage;
    @FXML
    private Button startAmbientBtn;

    public void initialize() {

        InputStream girlStream = getClass().getResourceAsStream("/images/girl/girl_with_headphones.png");
        if (girlStream != null && girlImage != null) {
            girlImage.setImage(new Image(girlStream));
        }
    }

    @FXML
    private void goToAmbient() {
        Stage stage = (Stage) startAmbientBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/AmbientSounds.fxml", "Sense Break — Ambient Sounds");
    }
}
