package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONObject;
import sensebreak.gui.AuthSession;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Button loginButton;
    @FXML private ImageView illustration;
    @FXML private ImageView logo;

    @FXML
    public void initialize() {
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

        if (!email.contains("@") || password.length() < 6) {
            emailError.setText("Invalid email or password");
            passwordError.setText("Invalid email or password");
            return;
        }

        try {
            // JSON tělo
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


    @FXML
    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1300, 900);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sense Break — Register");
            stage.setResizable(false);
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
