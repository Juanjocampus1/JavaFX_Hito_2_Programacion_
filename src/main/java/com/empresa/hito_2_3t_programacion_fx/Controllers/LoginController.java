package com.empresa.hito_2_3t_programacion_fx.Controllers;

import com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Response.GetUserResponse;
import com.empresa.hito_2_3t_programacion_fx.APIs.LoginApi.HTTP.Request.ApiRequestService;
import com.empresa.hito_2_3t_programacion_fx.DTO.LoginDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.TokenDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.UserDTO;
import com.empresa.hito_2_3t_programacion_fx.Persistence.TokenStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML
    private Button back;

    @FXML
    private Label message;

    private final ApiRequestService apiRequestService;
    private final GetUserResponse getUserRequest;
    private TokenDTO tokenDTO;

    public LoginController() {
        this.apiRequestService = new ApiRequestService();
        this.getUserRequest = new GetUserResponse();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void back() {
        try {
            Parent mainView = FXMLLoader.load(getClass().getResource("/com/empresa/hito_2_3t_programacion_fx/views/register.fxml"));
            Scene mainScene = new Scene(mainView);

            Stage currentStage = (Stage) back.getScene().getWindow();
            currentStage.setScene(mainScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loginUser() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            LoginDTO loginDTO = new LoginDTO(username, password);
            tokenDTO = apiRequestService.loginUser(loginDTO);

            usernameField.clear();
            passwordField.clear();

            if (tokenDTO != null && tokenDTO.getToken() != null) {
                // Almacenar el token en TokenStorage
                TokenStorage.setToken(tokenDTO.getToken());

                Parent mainView = FXMLLoader.load(getClass().getResource("/com/empresa/hito_2_3t_programacion_fx/views/main-view.fxml"));
                Scene mainScene = new Scene(mainView);

                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.setScene(mainScene);
            } else {
                showMessage("Usuario o contraseña incorrectos");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            if (e.getMessage().contains("Not a JSON Object: null")) {
                showMessage("Usuario o contraseña incorrectos");
            } else {
                throw e;
            }
        }
    }

    private void showMessage(String msg) {
        message.setText(msg);
        message.getStyleClass().add("visible");
    }


    @FXML
    protected void forgotPassword() {
        // Implement your forgot password logic here
    }
}