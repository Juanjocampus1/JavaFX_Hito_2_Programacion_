package com.empresa.hito_2_3t_programacion_fx.APIs.LoginApi.HTTP.Request;

import com.empresa.hito_2_3t_programacion_fx.DTO.LoginDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.LoginDTOTypeAdapter;
import com.empresa.hito_2_3t_programacion_fx.DTO.TokenDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.UserRegistrationDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.adapters.UserRegistrationDTOTypeAdapter;
import com.google.gson.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequestService {
    private static final String API_URL = "http://localhost:8081"; // Reemplaza con la URL de tu API
    private final HttpClient httpClient;
    private final Gson gson;

    public ApiRequestService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(UserRegistrationDTO.class, new UserRegistrationDTOTypeAdapter())
                .registerTypeAdapter(LoginDTO.class, new LoginDTOTypeAdapter())
                .create();
    }

    public TokenDTO loginUser(LoginDTO loginDTO) {
        String requestBody = gson.toJson(loginDTO);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/api/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body() != null && !response.body().isEmpty()) {
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                return gson.fromJson(jsonResponse, TokenDTO.class);
            } else {
                System.out.println("Error: Response body is null or empty");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}