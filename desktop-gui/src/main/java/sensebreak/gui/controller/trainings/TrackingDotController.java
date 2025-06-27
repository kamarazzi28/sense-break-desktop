package sensebreak.gui.controller.trainings;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sensebreak.gui.AuthSession;
import sensebreak.gui.Router;
import sensebreak.gui.controller.components.TrainingControlsController;
import sensebreak.gui.controller.components.TrainingHeaderController;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class TrackingDotController {

    @FXML
    private Canvas canvas;

    @FXML
    private AnchorPane controlsInclude;

    @FXML
    private AnchorPane trainingHeaderPane;

    private Timeline animationTimer;
    private Timeline sessionTimer;

    private TrainingHeaderController trainingHeader;

    private boolean horizontal = true;
    private double dotPos = 0;
    private boolean forward = true;

    private int secondsElapsed = 0;
    private boolean isPaused = false;

    public void initialize() {
        TrainingControlsController trainingControls =
                (TrainingControlsController) controlsInclude.getProperties().get("controller");
        TrainingHeaderController headerCtrl =
                (TrainingHeaderController) trainingHeaderPane.getProperties().get("controller");

        if (trainingControls != null) {
            trainingControls.setOnPause(this::togglePause);
            trainingControls.setOnSettings(this::openSettings);
            trainingControls.setOnHelp(this::openHelp);
        }

        if (headerCtrl != null) {
            this.trainingHeader = headerCtrl;
            headerCtrl.setOnCloseConfirmed(() -> {
                stopTraining();
                Router.goBack();
            });
        }

        startTraining();
    }

    private void startTraining() {
        if (trainingHeader != null) {
            trainingHeader.updateTimerLabel("00:00");
        }
        sessionTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateSessionTimer()));
        sessionTimer.setCycleCount(60);
        sessionTimer.setOnFinished(e -> handleTrainingFinished());
        sessionTimer.play();

        animationTimer = new Timeline(new KeyFrame(Duration.millis(16), e -> updateAnimation()));
        animationTimer.setCycleCount(Timeline.INDEFINITE);
        animationTimer.play();
    }


    private void updateSessionTimer() {
        if (isPaused) return;

        secondsElapsed++;
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;

        if (trainingHeader != null) {
            trainingHeader.updateTimerLabel(String.format("%02d:%02d", minutes, seconds));
        }

        if (secondsElapsed % 15 == 0) {
            horizontal = !horizontal;
        }
    }

    private void updateAnimation() {
        if (isPaused) return;

        dotPos += forward ? 0.01 : -0.01;
        if (dotPos >= 1) {
            dotPos = 1;
            forward = false;
        } else if (dotPos <= 0) {
            dotPos = 0;
            forward = true;
        }

        drawDot();
    }

    private void drawDot() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int canvasWidth = 1200;
        int canvasHeight = 700;
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        double x = canvasWidth / 2.0;
        double y = canvasHeight / 2.0;

        if (horizontal) {
            x = 50 + dotPos * (canvasWidth - 100);
        } else {
            y = 50 + dotPos * (canvasHeight - 100);
        }

        gc.setFill(Color.web("#7B3FF2"));
        int dotRadius = 20;
        gc.fillOval(x - dotRadius, y - dotRadius, dotRadius * 2, dotRadius * 2);
    }

    private void togglePause() {
        isPaused = !isPaused;
    }

    private void openHelp() {
        togglePause();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to use Tracking Dot");
        alert.setContentText("Follow the moving dot with your eyes to the screen’s edge, moving your gaze as far as comfortable.\n\nDo not strain your eyes—move them gently and take breaks if needed.");
        alert.setOnHidden(e -> togglePause());
        alert.show();
    }

    private void openSettings() {
        togglePause();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings");
        alert.setHeaderText("Dot settings (not editable yet)");
        alert.setContentText("Soon you’ll be able to change the dot size, speed and trajectory here.");
        alert.setOnHidden(e -> togglePause());
        alert.show();
    }

    private void handleTrainingFinished() {
        if (animationTimer != null) animationTimer.stop();
        if (sessionTimer != null) sessionTimer.stop();

        sendTrainingCompleted();

        Alert done = new Alert(Alert.AlertType.INFORMATION);
        done.setTitle("Training Completed");
        done.setHeaderText("Great job!");
        done.setContentText("You’ve successfully finished the vision training.");

        done.setOnHidden(e -> Router.goBack());
        done.show();
    }


    private void stopTraining() {
        if (animationTimer != null) animationTimer.stop();
        if (sessionTimer != null) sessionTimer.stop();

    }

    private void sendTrainingCompleted() {
        try {
            UUID userId = AuthSession.getUserId();
            String token = AuthSession.getToken();
            if (userId == null || token == null) return;

            URL url = new URL("http://localhost:8080/api/progress/finish-training?type=VISION");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            if (conn.getResponseCode() == 200) {
                System.out.println("Training completion sent");
            } else {
                System.err.println("Failed to send training completion: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
