package sensebreak.gui.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class TrainingControlsController {

    @FXML
    private Button settingsBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private Button helpBtn;
    @FXML
    private AnchorPane root;

    private boolean isPaused = false;
    private Runnable onPause;
    private Runnable onSettings;
    private Runnable onHelp;

    public void setOnPause(Runnable onPause) {
        this.onPause = onPause;
    }

    public void setOnSettings(Runnable onSettings) {
        this.onSettings = onSettings;
    }

    public void setOnHelp(Runnable onHelp) {
        this.onHelp = onHelp;
    }

    @FXML
    public void initialize() {
        settingsBtn.setText("⚙");
        backBtn.setText("⏮");
        pauseBtn.setText("⏸");
        nextBtn.setText("⏭");
        helpBtn.setText("?");

        if (root != null) {
            root.getProperties().put("controller", this);
        }

        pauseBtn.setOnAction(e -> {
            isPaused = !isPaused;
            pauseBtn.setText(isPaused ? "▶" : "⏸");
            if (onPause != null) onPause.run();
        });
        settingsBtn.setOnAction(e -> {
            if (onSettings != null) onSettings.run();
        });
        helpBtn.setOnAction(e -> {
            if (onHelp != null) onHelp.run();
        });
    }
}
