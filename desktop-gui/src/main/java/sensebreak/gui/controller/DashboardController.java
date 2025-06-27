package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sensebreak.gui.AuthSession;
import sensebreak.gui.Router;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

/**
 * Controller for the main dashboard screen.
 * Displays a greeting, current date, current user streak,
 * and provides access to different training modes.
 */
public class DashboardController {

    @FXML
    private Label greetingLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView fireIcon;
    @FXML
    private ImageView girlImage;
    @FXML
    private Label streakTitle;
    @FXML
    private Button visionBtn;
    @FXML
    private Button hearingBtn;
    @FXML
    private Button startTrainingBtn;

    /**
     * Initializes the dashboard screen with user-specific data:
     * greeting, date, streak, and UI images.
     */
    @FXML
    public void initialize() {
        String email = AuthSession.getUsername();
        String username;

        if (email == null || email.isEmpty()) {
            username = "User";
        } else if (email.contains("@")) {
            username = email.substring(0, email.indexOf("@"));
            username = username.substring(0, 1).toUpperCase() + username.substring(1);
        } else {
            username = email;
        }

        greetingLabel.setText(getGreeting() + ", " + username + "!");
        dateLabel.setText("Today is " + getFormattedDate());

        InputStream fireStream = getClass().getResourceAsStream("/images/figures/fire.png");
        if (fireStream != null && fireIcon != null) {
            fireIcon.setImage(new Image(fireStream));
        }

        InputStream girlStream = getClass().getResourceAsStream("/images/girl/girl_with_streak.png");
        if (girlStream != null && girlImage != null) {
            girlImage.setImage(new Image(girlStream));
        }

        UUID userId = AuthSession.getUserId();
        if (userId != null) {
            int streak = fetchCurrentStreak(userId);
            streakTitle.setText(streak + "-day streak!");
        } else {
            streakTitle.setText("error");
        }
    }

    /**
     * Sends a GET request to backend to fetch current streak for the user.
     *
     * @param userId ID of the currently logged-in user.
     * @return integer value representing the current streak.
     */
    private int fetchCurrentStreak(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/streak?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            String token = AuthSession.getToken();
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String response = reader.readLine();
                    return Integer.parseInt(response);
                }
            } else {
                System.err.println("Failed to fetch streak: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Generates a greeting based on the time of day.
     *
     * @return greeting string
     */
    private String getGreeting() {
        int hour = LocalDate.now().atStartOfDay().getHour();
        if (hour >= 5 && hour < 12) {
            return "Good morning";
        } else if (hour >= 12 && hour < 18) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }

    /**
     * Returns today's date formatted as: "DayOfWeek, Month Day".
     *
     * @return formatted date string
     */
    private String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        return formatter.format(LocalDate.now());
    }

    /**
     * Starts the Vision training scene.
     */
    @FXML
    private void startVisionTraining() {
        Stage stage = (Stage) visionBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/Vision.fxml", "Sense Break — Vision");
    }

    /**
     * Starts the Hearing training scene.
     */
    @FXML
    private void startHearingTraining() {
        Stage stage = (Stage) hearingBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/Hearing.fxml", "Sense Break — Hearing");
    }

    /**
     * Starts the daily default training scene.
     */
    @FXML
    private void startDailyTraining() {
        Stage stage = (Stage) startTrainingBtn.getScene().getWindow();
        Router.switchScene(stage, "/fxml/TrackingDot.fxml", "Sense Break — Tracking Dot");
    }
}
