package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class HearingController {
    @FXML
    private ImageView girlImage;

    public void initialize() {

        InputStream girlStream = getClass().getResourceAsStream("/images/girl/girl_with_headphones.png");
        if (girlStream != null && girlImage != null) {
            girlImage.setImage(new Image(girlStream));
        }
    }
}
