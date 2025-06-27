package sensebreak.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Utility class responsible for handling scene navigation within the JavaFX application.
 */
public class Router {

    private static Stage primaryStage;
    private static String currentPageId;

    /**
     * Initializes the primary stage reference for the application.
     *
     * @param stage the main application stage
     */
    public static void init(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Switches to a new scene with the specified FXML path, title, and page ID.
     *
     * @param stage    the stage to set the scene on
     * @param fxmlPath the path to the FXML file
     * @param title    the window title
     * @param pageId   optional identifier for the page
     */
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

    /**
     * Overloaded method to switch scenes without specifying a page ID.
     *
     * @param stage    the stage to set the scene on
     * @param fxmlPath the path to the FXML file
     * @param title    the window title
     */
    public static void switchScene(Stage stage, String fxmlPath, String title) {
        switchScene(stage, fxmlPath, title, null);
    }

    /**
     * Navigates back to the default dashboard scene.
     */
    public static void goBack() {
        switchScene(primaryStage, "/fxml/Dashboard.fxml", "Sense Break — Dashboard");
    }
}
