package com.empresa.hito_2_3t_programacion_fx.Controllers;

import com.empresa.hito_2_3t_programacion_fx.APIs.RegisterApi.HTTP.Request.ApiRequestService;
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

    private void registerUser() {
        //TODO
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