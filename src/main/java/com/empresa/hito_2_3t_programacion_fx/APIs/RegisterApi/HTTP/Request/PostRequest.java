package com.empresa.hito_2_3t_programacion_fx.APIs.RegisterApi.HTTP.Request;

import com.empresa.hito_2_3t_programacion_fx.DTO.UserRegistrationDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.adapters.UserRegistrationDTOTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequest {

    public static int sendPostRequest(UserRegistrationDTO user) throws Exception {
        URL url = new URL("http://localhost:8081/api/user/createUser");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(UserRegistrationDTO.class, new UserRegistrationDTOTypeAdapter())
                .create();

        String jsonInputString = gson.toJson(user);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        return connection.getResponseCode();
    }
}
