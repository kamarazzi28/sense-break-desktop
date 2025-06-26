package sensebreak.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sensebreak.gui.AuthSession;
import sensebreak.gui.Router;

import java.util.Objects;

public class SidebarController {

    @FXML
    private VBox sidebar;
    @FXML
    private ImageView logo;
    @FXML
    private Button dashboardBtn;
    @FXML
    private Button visionBtn;
    @FXML
    private Button hearingBtn;
    @FXML
    private Button progressBtn;
    @FXML
    private Button notificationsBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    public void initialize() {
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo/sb_logo.png")));
        logo.setImage(logoImage);

        Platform.runLater(() -> dashboardBtn.requestFocus());

        dashboardBtn.setOnAction(e -> Router.switchScene(getStage(), "/fxml/Dashboard.fxml", "Sense Break — Dashboard"));
        visionBtn.setOnAction(e -> Router.switchScene(getStage(), "/fxml/Vision.fxml", "Sense Break — Vision"));
        hearingBtn.setOnAction(e -> Router.switchScene(getStage(), "/fxml/Hearing.fxml", "Sense Break — Hearing"));
        progressBtn.setOnAction(e -> Router.switchScene(getStage(), "/fxml/Progress.fxml", "Sense Break — Progress"));
        notificationsBtn.setOnAction(e -> Router.switchScene(getStage(), "/fxml/Notifications.fxml", "Sense Break — Notifications"));
        settingsBtn.setOnAction(e -> Router.switchScene(getStage(), "/fxml/Settings.fxml", "Sense Break — Settings"));

        logoutBtn.setOnAction(e -> handleLogout());
    }

    private Stage getStage() {
        return (Stage) sidebar.getScene().getWindow();
    }

    private void handleLogout() {
        logoutBtn.setDefaultButton(false);
        logoutBtn.setFocusTraversable(false);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This action will log you out from your account.");

        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType logoutButton = new ButtonType("Logout");

        alert.getButtonTypes().setAll(cancelButton, logoutButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == logoutButton) {
                AuthSession.setToken(null);
                AuthSession.setUsername(null);
                AuthSession.setUserId(null);
                Router.switchScene(getStage(), "/fxml/Login.fxml", "Sense Break — Login");
            } else {
                Platform.runLater(() -> dashboardBtn.requestFocus());
            }
        });
    }
}
