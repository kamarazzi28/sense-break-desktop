package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sensebreak.gui.Router;
import javafx.scene.control.Button;

import java.io.InputStream;

/**
 * Controller for the Hearing training screen.
 * Displays themed image and navigates to ambient sound training.
 */
public class HearingController {

    @FXML
    private ImageView girlImage;

    @FXML
    private Button startAmbientBtn;

    /**
     * Initializes the screen by loading the image of the girl with headphones.
     */
    public void initialize() {
        InputStream girlStream = getClass().getResourceAsStream("/images/girl/girl_with_headphones.png");
        if (girlStream != null && girlImage != null) {
            girlImage.setImage(new Image(girlStream));
        }
    }

    /**
     * Navigates to the Ambient Sounds training screen.
     */
    @FXML
    private void goToAmbient() {
        Stage stage = (Stage) startAmbientBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/AmbientSounds.fxml", "Sense Break — Ambient Sounds");
    }
}
