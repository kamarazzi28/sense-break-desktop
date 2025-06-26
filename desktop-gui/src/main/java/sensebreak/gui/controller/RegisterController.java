package sensebreak.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;
import sensebreak.gui.Router;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class RegisterController {

    public VBox leftPane;
    public Button registerButton;
    public TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label emailError;
    @FXML
    private Label passwordError;
    @FXML
    private Label usernameError;


    @FXML
    private ImageView logo;
    @FXML
    private ImageView illustration;

    public RegisterController() {
    }

    @FXML
    private void initialize() {
        logo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo/sb_logo_with_white_bg.png"))));
        illustration.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/girl/girl_registration.png"))));
    }

    private void showSuccessAndRedirectToLogin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Account created successfully.\nPlease log in to continue.");
        alert.showAndWait();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerButton.getScene().getWindow();

            Scene scene = new Scene(root, 1300, 900);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

            stage.setScene(scene);
            stage.setResizable(false);
            root.requestFocus();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        usernameError.setText("");
        emailError.setText("");
        passwordError.setText("");

        if (username.length() < 3 || !email.contains("@") || password.length() < 6) {
            emailError.setText("Invalid input");
            return;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("email", email);
            json.put("password", password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                showSuccessAndRedirectToLogin();
            } else {
                emailError.setText("Registration failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            emailError.setText("Error occurred");
        }
    }

    @FXML
    private void goToLogin() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Router.switchScene(stage, "/fxml/Login.fxml", "Sense Break — Login");
    }


}
