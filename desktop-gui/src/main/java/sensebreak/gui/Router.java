package sensebreak.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Router {
    private static Stage primaryStage;
    private static String currentPageId;

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void switchScene(Stage stage, String fxmlPath, String title, String pageId) {
        try {
            currentPageId = pageId;
            FXMLLoader loader = new FXMLLoader(Router.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1300, 900);
            scene.getStylesheets().add(Objects.requireNonNull(Router.class.getResource("/css/style.css")).toExternalForm());

            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();

            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchScene(Stage stage, String fxmlPath, String title) {
        switchScene(stage, fxmlPath, title, null);
    }
}
