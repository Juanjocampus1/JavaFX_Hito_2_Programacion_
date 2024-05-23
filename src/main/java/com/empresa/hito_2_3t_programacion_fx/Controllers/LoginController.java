package com.empresa.hito_2_3t_programacion_fx.Controllers;

import com.empresa.hito_2_3t_programacion_fx.APIs.LoginApi.HTTP.Request.ApiRequestService;
import com.empresa.hito_2_3t_programacion_fx.DTO.LoginDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private final ApiRequestService apiRequestService;

    public LoginController() {
        this.apiRequestService = new ApiRequestService();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void loginUser() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginDTO loginDTO = new LoginDTO(username, password);
            String token = apiRequestService.loginUser(loginDTO);

            if (token != null) {
                Parent mainView = FXMLLoader.load(getClass().getResource("/com/empresa/hito_2_3t_programacion_fx/views/main-view.fxml"));
                Scene mainScene = new Scene(mainView);

                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.setScene(mainScene);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}