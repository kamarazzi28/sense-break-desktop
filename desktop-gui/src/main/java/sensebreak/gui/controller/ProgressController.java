package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sensebreak.gui.AuthSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Controller responsible for displaying user progress data on the Progress screen.
 * It retrieves streaks, trainings, and relaxation statistics from the backend API.
 */
public class ProgressController {
    @FXML
    private Label currentStreak;
    @FXML
    private Label longestStreak;
    @FXML
    private Label relaxationMinutes;
    @FXML
    private Label totalTrainings;
    @FXML
    private Label visionTrainings;
    @FXML
    private Label hearingTrainings;

    /**
     * Initializes the progress screen by fetching and displaying user statistics.
     */
    public void initialize() {
        UUID userId = AuthSession.getUserId();
        if (userId != null) {
            int lonStreak = fetchLongestStreak(userId);
            int curStreak = fetchCurrentStreak(userId);
            int hearTrainings = fetchHearingTrainings(userId);
            int visTrainings = fetchVisionTrainings(userId);
            int totTrainings = fetchTotalTrainings(userId);
            hearingTrainings.setText(String.valueOf(hearTrainings));
            visionTrainings.setText(String.valueOf(visTrainings));
            totalTrainings.setText(String.valueOf(totTrainings));
            currentStreak.setText(String.valueOf(curStreak));
            longestStreak.setText(String.valueOf(lonStreak));
        } else {
            longestStreak.setText("error");
            currentStreak.setText("error");
            hearingTrainings.setText("error");
            visionTrainings.setText("error");
            totalTrainings.setText("error");
        }
        int relaxMinutes = fetchRelaxationMinutes(userId);
        relaxationMinutes.setText(relaxMinutes + " min");
    }

    /**
     * Fetches current streak value for the given user.
     */
    private int fetchCurrentStreak(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/streak?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    return Integer.parseInt(reader.readLine());
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
     * Fetches longest streak value for the given user.
     */
    private int fetchLongestStreak(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/longest-streak?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    return Integer.parseInt(reader.readLine());
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
     * Fetches count of hearing trainings for the given user.
     */
    private int fetchHearingTrainings(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/hearing-trainings?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    return Integer.parseInt(reader.readLine());
                }
            } else {
                System.err.println("Failed to fetch hearing trainings: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Fetches count of vision trainings for the given user.
     */
    private int fetchVisionTrainings(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/vision-trainings?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    return Integer.parseInt(reader.readLine());
                }
            } else {
                System.err.println("Failed to fetch vision trainings: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Fetches total number of trainings for the given user.
     */
    private int fetchTotalTrainings(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/total-trainings?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    return Integer.parseInt(reader.readLine());
                }
            } else {
                System.err.println("Failed to fetch total trainings: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Fetches total relaxation minutes recorded for the given user.
     */
    private int fetchRelaxationMinutes(UUID userId) {
        try {
            URL url = new URL("http://localhost:8080/api/progress/relaxation-minutes?userId=" + userId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());

            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    return Integer.parseInt(reader.readLine());
                }
            } else {
                System.err.println("Failed to fetch relaxation minutes: " + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
