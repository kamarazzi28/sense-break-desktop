package sensebreak.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Router.init(stage);

            Router.switchScene(stage, "/fxml/AmbientSounds.fxml", "Sense Break — Dashboard");

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo/sb_logo.png"))));
            stage.setTitle("Sense Break");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
