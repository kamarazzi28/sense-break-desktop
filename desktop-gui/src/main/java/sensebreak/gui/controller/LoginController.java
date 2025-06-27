package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONObject;
import sensebreak.gui.AuthSession;
import sensebreak.gui.Router;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

/**
 * Controller responsible for handling user login logic and UI.
 * Authenticates the user via HTTP request and redirects to the dashboard if successful.
 */
public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Button loginButton;
    @FXML
    private ImageView illustration;
    @FXML
    private ImageView logo;

    /**
     * Initializes the login page by loading logo and illustration images.
     */
    @FXML
    public void initialize() {
        try {
            Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo/sb_logo_with_white_bg.png")));
            logo.setImage(logoImage);
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/girl/girl_jumping.png")));
            illustration.setImage(img);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    /**
     * Switches the current scene to the dashboard after successful login.
     */
    private void goToDashboard() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Router.switchScene(stage, "/fxml/Dashboard.fxml", "Sense Break — Dashboard", "dashboard");
    }

    /**
     * Handles login action when the login button is pressed.
     * Sends login credentials to backend and stores session if successful.
     */
    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        emailError.setText("");
        passwordError.setText("");

        if (!email.contains("@") || password.length() < 6) {
            emailError.setText("Invalid email or password");
            passwordError.setText("Invalid email or password");
            return;
        }

        try {
            String json = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                String token = obj.getString("token");
                JSONObject user = obj.getJSONObject("user");
                String username = user.getString("username");

                String idString = user.getString("id");
                UUID userId = UUID.fromString(idString);
                AuthSession.setUserId(userId);

                AuthSession.setToken(token);
                AuthSession.setUsername(username);

                goToDashboard();
            } else {
                emailError.setText("Invalid email or password");
                passwordError.setText("Invalid email or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            emailError.setText("Server error");
        }
    }

    /**
     * Navigates the user to the registration page.
     */
    @FXML
    private void goToRegister() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Router.switchScene(stage, "/fxml/Register.fxml", "Sense Break — Register");
    }
}
