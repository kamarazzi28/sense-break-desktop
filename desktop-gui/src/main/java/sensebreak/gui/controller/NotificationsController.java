package sensebreak.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sensebreak.gui.AuthSession;
import sensebreak.gui.dto.ReminderSettings;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationsController {

    @FXML
    private ToggleButton toggleNotifications;

    @FXML
    private ComboBox<String> reminderCombo;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8080/api/progress/reminders";

    private Timer reminderTimer;

    @FXML
    public void initialize() {
        reminderCombo.getItems().addAll("Every 30 min", "Every hour", "Every 3 hours");

        String url = BASE_URL + "?userId=" + AuthSession.getUserId();
        ResponseEntity<ReminderSettings> response = restTemplate.getForEntity(URI.create(url), ReminderSettings.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ReminderSettings settings = response.getBody();
            toggleNotifications.setSelected(settings.isRemindersEnabled());
            switch (settings.getReminderIntervalMinutes()) {
                case 30 -> reminderCombo.setValue("Every 30 min");
                case 60 -> reminderCombo.setValue("Every hour");
                case 180 -> reminderCombo.setValue("Every 3 hours");
                default -> reminderCombo.setValue("Choose Time");
            }
        }

        toggleNotifications.setOnAction(e -> saveSettings());
        reminderCombo.setOnAction(e -> saveSettings());

        startReminderTimer();
    }

    private void saveSettings() {
        ReminderSettings updated = new ReminderSettings();
        updated.setRemindersEnabled(toggleNotifications.isSelected());

        String selected = reminderCombo.getValue();
        if (selected == null) return;

        switch (selected) {
            case "Every 30 min" -> updated.setReminderIntervalMinutes(30);
            case "Every hour" -> updated.setReminderIntervalMinutes(60);
            case "Every 3 hours" -> updated.setReminderIntervalMinutes(180);
        }

        String url = BASE_URL + "?userId=" + AuthSession.getUserId();
        restTemplate.put(URI.create(url), updated);

        startReminderTimer();
    }

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
                Platform.runLater(() -> showReminder());
            }
        }, intervalMinutes * 60 * 1000L, intervalMinutes * 60 * 1000L);
    }

    private void showReminder() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reminder");
        alert.setHeaderText(null);
        alert.setContentText("Time to do an eye or hearing training!");
        alert.show();
    }
}
