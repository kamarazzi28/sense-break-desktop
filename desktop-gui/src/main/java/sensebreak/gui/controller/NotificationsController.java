package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;

public class NotificationsController {

    @FXML
    private ToggleButton toggleNotifications;

    @FXML
    private ComboBox<String> reminderCombo;

    @FXML
    private Label statusLabel;

    private boolean notificationsEnabled = true;
    private String selectedReminder = "Every hour";

    @FXML
    public void initialize() {
        toggleNotifications.setSelected(notificationsEnabled);
        updateToggleStyle();

        reminderCombo.getItems().addAll("Every 30 min", "Every hour", "Every 3 hours");
        reminderCombo.setValue(selectedReminder);

        toggleNotifications.selectedProperty().addListener((obs, oldVal, newVal) -> {
            notificationsEnabled = newVal;
            updateToggleStyle();
            System.out.println("Notifications enabled: " + notificationsEnabled);
        });

        reminderCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            selectedReminder = newVal;
            System.out.println("Selected reminder: " + selectedReminder);
        });
    }

    private void updateToggleStyle() {
        toggleNotifications.getStyleClass().removeAll("custom-toggle");
        toggleNotifications.getStyleClass().add("custom-toggle");
    }
}
