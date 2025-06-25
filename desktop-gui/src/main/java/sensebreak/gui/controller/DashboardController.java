package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sensebreak.gui.AuthSession;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label dateLabel;
    @FXML private Label trainingText;
    @FXML private Label streakLabel;

    @FXML
    public void initialize() {
        String username = AuthSession.getUsername();
        welcomeLabel.setText("Good evening, " + username + "!");
        dateLabel.setText("Today is " + formatDate(LocalDate.now()));

        int streak = 0; // позже сюда можно передавать реальные данные
        streakLabel.setText(streak + "-day streak!");
        trainingText.setText("You're on a " + streak + "-day streak.\nKeep going to build a healthy habit!");
    }

    private String formatDate(LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " +
                date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " +
                date.getDayOfMonth();
    }

    @FXML
    private void startTraining() {
        System.out.println("Starting today's session...");
        // TODO: открыть конкретный training.fxml
    }

    @FXML
    private void startVision() {
        System.out.println("Starting vision training...");
    }

    @FXML
    private void startHearing() {
        System.out.println("Starting hearing training...");
    }
}
