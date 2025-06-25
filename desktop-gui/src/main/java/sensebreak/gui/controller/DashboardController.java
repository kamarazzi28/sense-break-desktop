package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sensebreak.gui.AuthSession;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DashboardController {

    @FXML private Label greetingLabel;
    @FXML private Label dateLabel;

    @FXML private VBox visionCard;
    @FXML private VBox hearingCard;

    @FXML private ImageView logo;
    @FXML private ImageView fireIcon;
    @FXML private ImageView girlImage;

    @FXML private VBox navButtons;
    @FXML private VBox settingsSection;
    @FXML private VBox mainContent;

    @FXML private Button dashboardBtn;
    @FXML private Button visionBtn;
    @FXML private Button hearingBtn;
    @FXML private Button progressBtn;
    @FXML private Button notificationsBtn;
    @FXML private Button settingsBtn;

    @FXML private Button startTrainingBtn;
    @FXML private Label streakTitle;
    @FXML private Button visionStartBtn;
    @FXML private Button hearingStartBtn;

    @FXML
    public void initialize() {
        String username = AuthSession.getUsername();
        if (username == null || username.isEmpty()) {
            username = "User";
        }

        greetingLabel.setText(getGreeting() + ", " + username + "!");
        dateLabel.setText("Today is " + getFormattedDate());

        InputStream logoStream = getClass().getResourceAsStream("/images/logo/sb_logo.png");
        if (logoStream != null) {
            logo.setImage(new Image(logoStream));
        }

        InputStream fireStream = getClass().getResourceAsStream("/images/figures/fire.png");
        if (fireStream != null && fireIcon != null) {
            fireIcon.setImage(new Image(fireStream));
        }

        InputStream girlStream = getClass().getResourceAsStream("/images/girl/girl_with_streak.png");
        if (girlStream != null && girlImage != null) {
            girlImage.setImage(new Image(girlStream));
        }
    }

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

    private String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        return formatter.format(LocalDate.now());
    }

    @FXML
    private void startVisionTraining() {
        System.out.println("Start Vision Training");
        // TODO: přepnout na Vision tréninkovou stránku
    }

    @FXML
    private void startHearingTraining() {
        System.out.println("Start Hearing Training");
        // TODO: přepnout na Hearing tréninkovou stránku
    }
}
