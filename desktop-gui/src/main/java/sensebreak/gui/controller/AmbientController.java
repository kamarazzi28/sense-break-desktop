package sensebreak.gui.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sensebreak.gui.AuthSession;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

/**
 * Controller for handling ambient sound playback in the GUI.
 * Allows user to play one of three ambient tracks (birds, rain, ocean),
 * displays a playback timer, and sends relaxation time to the backend.
 */
public class AmbientController {

    @FXML
    private ImageView birdsImage;
    @FXML
    private ImageView rainImage;
    @FXML
    private ImageView oceanImage;

    @FXML
    private Label birdsTimer;
    @FXML
    private Label rainTimer;
    @FXML
    private Label oceanTimer;

    @FXML
    private Button birdsBtn;
    @FXML
    private Button rainBtn;
    @FXML
    private Button oceanBtn;

    private Timeline timer;
    private long startTime;
    private boolean isPlaying = false;
    private String currentSound = null;
    private MediaPlayer mediaPlayer;

    private Image playIcon;
    private Image stopIcon;

    private int lastSentMinute = 0;
    public static AmbientController instance;

    /**
     * Initializes the controller, sets up UI elements and event handlers.
     */
    public void initialize() {
        instance = this;
        setImage(birdsImage, "/images/pics/birds.png");
        setImage(rainImage, "/images/pics/rain.png");
        setImage(oceanImage, "/images/pics/ocean.png");

        playIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/play.png")));
        stopIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icons/stop.png")));

        birdsBtn.setGraphic(createResizedIcon(playIcon));
        rainBtn.setGraphic(createResizedIcon(playIcon));
        oceanBtn.setGraphic(createResizedIcon(playIcon));

        birdsBtn.setOnAction(e -> toggleAudio("birds"));
        rainBtn.setOnAction(e -> toggleAudio("rain"));
        oceanBtn.setOnAction(e -> toggleAudio("ocean"));
    }

    private void setImage(ImageView view, String path) {
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream != null) view.setImage(new Image(stream));
    }

    private void toggleAudio(String soundId) {
        if (Objects.equals(currentSound, soundId)) {
            stopAudio();
        } else {
            playAudio(soundId);
        }
    }

    private void playAudio(String soundId) {
        stopAudio();
        currentSound = soundId;
        isPlaying = true;
        startTime = System.currentTimeMillis();

        getTimerLabel(soundId).setVisible(true);
        getToggleButton(soundId).setGraphic(createResizedIcon(stopIcon));

        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        String filename = switch (soundId) {
            case "birds" -> "/sounds/morning-birds.wav";
            case "rain" -> "/sounds/rain-long-loop.wav";
            case "ocean" -> "/sounds/waves.wav";
            default -> null;
        };

        if (filename != null) {
            try {
                Media media = new Media(Objects.requireNonNull(getClass().getResource(filename)).toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopAudio() {
        isPlaying = false;
        currentSound = null;
        if (timer != null) timer.stop();

        birdsBtn.setGraphic(createResizedIcon(playIcon));
        rainBtn.setGraphic(createResizedIcon(playIcon));
        oceanBtn.setGraphic(createResizedIcon(playIcon));

        birdsTimer.setVisible(false);
        rainTimer.setVisible(false);
        oceanTimer.setVisible(false);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    /**
     * Static method to stop audio from other parts of the app.
     */
    public static void stopAudioIfPlaying() {
        if (instance != null) {
            instance.stopAudio();
        }
    }

    private void updateTimer() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long minutes = elapsed / 60;
        long seconds = elapsed % 60;
        Label label = getTimerLabel(currentSound);
        label.setVisible(true);
        if (minutes > 0)
            label.setText(minutes + " min playing");
        else
            label.setText(seconds + " sec playing");
        if (minutes > lastSentMinute) {
            lastSentMinute = (int) minutes;
            sendRelaxationMinute();
        }
    }

    private Label getTimerLabel(String id) {
        return switch (id) {
            case "birds" -> birdsTimer;
            case "rain" -> rainTimer;
            case "ocean" -> oceanTimer;
            default -> new Label();
        };
    }

    private Button getToggleButton(String id) {
        return switch (id) {
            case "birds" -> birdsBtn;
            case "rain" -> rainBtn;
            case "ocean" -> oceanBtn;
            default -> new Button();
        };
    }

    private ImageView createResizedIcon(Image icon) {
        ImageView view = new ImageView(icon);
        view.setFitWidth(20);
        view.setFitHeight(20);
        return view;
    }

    /**
     * Sends 1 minute of relaxation time to the backend for the currently authenticated user.
     */
    private void sendRelaxationMinute() {
        try {
            UUID userId = AuthSession.getUserId();
            String token = AuthSession.getToken();
            if (userId == null || token == null) return;

            URL url = new URL("http://localhost:8080/api/progress/relaxation-minutes?userId=" + userId + "&minutes=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setDoOutput(true);

            if (conn.getResponseCode() == 200) {
                System.out.println("Relaxation minute sent");
            } else {
                System.err.println("Failed to send minute: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
