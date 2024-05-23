package com.empresa.hito_2_3t_programacion_fx.Controllers;

import com.empresa.hito_2_3t_programacion_fx.APIs.LoginApi.HTTP.Request.ApiRequestService;
import com.empresa.hito_2_3t_programacion_fx.APIs.RegisterApi.HTTP.Request.PostRequest;
import com.empresa.hito_2_3t_programacion_fx.DTO.UserRegistrationDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button registerButton;
    @FXML
    private Button alreadyHaveAccountButton;

    private final ApiRequestService apiRequestService;

    public RegisterController() {
        this.apiRequestService = new ApiRequestService();
    }

    @FXML
    public void initialize() {
        registerButton.setOnAction(event -> registerUser());
    }

    @FXML
    private void registerUser() {
        try {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            UserRegistrationDTO user = UserRegistrationDTO.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .roles(Arrays.asList("USER"))
                    .build();

            int responseCode = PostRequest.sendPostRequest(user);

            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("User registered successfully.");
                Parent mainView = FXMLLoader.load(getClass().getResource("/com/empresa/hito_2_3t_programacion_fx/views/login.fxml"));
                Scene mainScene = new Scene(mainView);

                Stage currentStage = (Stage) registerButton.getScene().getWindow();
                currentStage.setScene(mainScene);
            } else {
                System.out.println("Failed to register user: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToLogin() {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/com/empresa/hito_2_3t_programacion_fx/views/login.fxml"));
            Scene loginScene = new Scene(loginView);

            Stage currentStage = (Stage) alreadyHaveAccountButton.getScene().getWindow();
            currentStage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}