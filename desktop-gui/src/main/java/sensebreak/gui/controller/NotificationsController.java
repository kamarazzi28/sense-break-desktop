package sensebreak.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import sensebreak.gui.AuthSession;
import sensebreak.gui.dto.ReminderSettings;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller responsible for managing user notification preferences.
 * Allows users to enable/disable reminders and choose reminder intervals.
 */
public class NotificationsController {

    @FXML
    private ToggleButton toggleNotifications;

    @FXML
    private ComboBox<String> reminderCombo;

    private final String BASE_URL = "http://localhost:8080/api/progress/reminders";
    private final ObjectMapper mapper = new ObjectMapper();
    private Timer reminderTimer;

    /**
     * Initializes the controller by loading settings and setting up event handlers.
     */
    @FXML
    public void initialize() {
        reminderCombo.getItems().addAll("Every 30 min", "Every hour", "Every 3 hours");
        loadSettings();
        toggleNotifications.setOnAction(e -> saveSettings());
        reminderCombo.setOnAction(e -> saveSettings());
    }

    /**
     * Loads reminder settings from the backend and updates the UI.
     */
    private void loadSettings() {
        try {
            URL url = new URL(BASE_URL + "?userId=" + AuthSession.getUserId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == 200) {
                InputStream in = conn.getInputStream();
                ReminderSettings settings = mapper.readValue(in, ReminderSettings.class);
                toggleNotifications.setSelected(settings.isRemindersEnabled());

                switch (settings.getReminderIntervalMinutes()) {
                    case 30 -> reminderCombo.setValue("Every 30 min");
                    case 60 -> reminderCombo.setValue("Every hour");
                    case 180 -> reminderCombo.setValue("Every 3 hours");
                    default -> reminderCombo.setValue("Choose Time");
                }

                startReminderTimer();
            } else {
                System.err.println("Failed to load settings: " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends updated reminder settings to the backend.
     */
    private void saveSettings() {
        try {
            ReminderSettings updated = new ReminderSettings();
            updated.setRemindersEnabled(toggleNotifications.isSelected());

            String selected = reminderCombo.getValue();
            if (selected == null) return;

            switch (selected) {
                case "Every 30 min" -> updated.setReminderIntervalMinutes(30);
                case "Every hour" -> updated.setReminderIntervalMinutes(60);
                case "Every 3 hours" -> updated.setReminderIntervalMinutes(180);
            }

            URL url = new URL(BASE_URL + "?userId=" + AuthSession.getUserId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + AuthSession.getToken());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            String json = mapper.writeValueAsString(updated);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            if (conn.getResponseCode() != 200) {
                System.err.println("Failed to save settings: " + conn.getResponseCode());
            }

            conn.disconnect();
            startReminderTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a local timer that periodically shows a reminder alert
     * based on the user's configured interval.
     */
    private void startReminderTimer() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
        }

        if (!toggleNotifications.isSelected()) return;

        String selected = reminderCombo.getValue();
        if (selected == null) return;

        int intervalMinutes = switch (selected) {
            case "Every 30 min" -> 30;
            case "Every hour" -> 60;
            case "Every 3 hours" -> 180;
            default -> 0;
        };

        if (intervalMinutes == 0) return;

        reminderTimer = new Timer(true);
        reminderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(NotificationsController.this::showReminder);
            }
        }, intervalMinutes * 60 * 1000L, intervalMinutes * 60 * 1000L);
    }

    /**
     * Displays a popup notification to remind the user to train.
     */
    private void showReminder() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reminder");
        alert.setHeaderText(null);
        alert.setContentText("Time to do an eye or hearing training!");
        alert.show();
    }
}
