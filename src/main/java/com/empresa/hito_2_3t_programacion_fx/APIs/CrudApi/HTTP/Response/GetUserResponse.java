package com.empresa.hito_2_3t_programacion_fx.APIs.CrudApi.HTTP.Response;

import com.empresa.hito_2_3t_programacion_fx.DTO.RoleDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.TokenDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.UserDTO;
import com.empresa.hito_2_3t_programacion_fx.Persistence.TokenStorage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetUserResponse {
    private static final String API_URL = "http://localhost:8081"; // Reemplaza con la URL de tu API
    private static final Logger LOGGER = Logger.getLogger(GetUserResponse.class.getName());
    private final HttpClient httpClient;

    public GetUserResponse() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public UserDTO getUserByToken() {
        // Obtener el token de TokenStorage
        String token = TokenStorage.getToken();
        LOGGER.info("Token content: " + token);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/api/auth/getUserByToken"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("HTTP Status Code: " + response.statusCode());
            LOGGER.info("API Response: " + response.body());

            // Parsea la respuesta JSON
            JSONObject jsonObject = new JSONObject(response.body());

            // Convierte el objeto JSON en un objeto UserDTO
            UserDTO userDTO = new UserDTO();
            userDTO.setId(jsonObject.getInt("id"));
            userDTO.setUsername(jsonObject.getString("username"));
            userDTO.setEmail(jsonObject.getString("email"));
            userDTO.setPassword(jsonObject.getString("password"));

            // Parsea el array JSON de roles
            JSONArray rolesArray = jsonObject.getJSONArray("roles");
            List<RoleDTO> roles = new ArrayList<>();
            for (int i = 0; i < rolesArray.length(); i++) {
                JSONObject roleObject = rolesArray.getJSONObject(i);
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(roleObject.getInt("id"));
                roleDTO.setName(roleObject.getString("name"));
                roles.add(roleDTO);
            }

            // Añade los roles al UserDTO
            userDTO.setRoles(roles);

            return userDTO;
        } catch (Exception e) {
            LOGGER.severe("Error al enviar solicitud GET: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}