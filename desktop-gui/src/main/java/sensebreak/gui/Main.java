package sensebreak.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Router.init(stage);
            Router.switchScene(stage, "/fxml/Login.fxml", "Sense Break — Login");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo/sb_logo.png")));
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
