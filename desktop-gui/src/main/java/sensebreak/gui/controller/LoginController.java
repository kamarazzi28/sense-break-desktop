package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Button loginButton;
    @FXML private Hyperlink recoveryLink;
    @FXML private ImageView illustration;
    @FXML private ImageView logo;

    @FXML
    public void initialize() {
        // Загрузка картинки прыгающей девочки
        try {
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo/sb_logo_with_white_bg.png"));
            logo.setImage(logoImage);
            Image img = new Image(getClass().getResourceAsStream("/images/girl/girl_jumping.png"));
            illustration.setImage(img);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        emailError.setText("");
        passwordError.setText("");

        boolean hasError = false;

        if (!email.contains("@")) {
            emailError.setText("Invalid email or password");
            hasError = true;
        }

        if (password.length() < 6) {
            passwordError.setText("Invalid email or password");
            hasError = true;
        }

        if (!hasError) {
            System.out.println("Logging in...");
            // TODO: Реализация авторизации через Firebase (REST или SDK)
        }
    }

    @FXML
    private void goToRegister() {
        System.out.println("Redirect to Register screen");
        // TODO: Переключение на Register.fxml
    }
}
