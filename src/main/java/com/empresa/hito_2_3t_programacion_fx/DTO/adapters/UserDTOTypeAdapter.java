package com.empresa.hito_2_3t_programacion_fx.DTO.adapters;

import com.empresa.hito_2_3t_programacion_fx.DTO.RoleDTO;
import com.empresa.hito_2_3t_programacion_fx.DTO.UserDTO;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDTOTypeAdapter implements JsonSerializer<UserDTO>, JsonDeserializer<UserDTO> {

    @Override
    public JsonElement serialize(UserDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("email", src.getEmail());
        jsonObject.addProperty("username", src.getUsername());
        jsonObject.addProperty("password", src.getPassword());
        JsonArray rolesArray = new JsonArray();
        for (RoleDTO role : src.getRoles()) {
            JsonObject roleObject = new JsonObject();
            roleObject.addProperty("id", role.getId());
            roleObject.addProperty("name", role.getName());
            rolesArray.add(roleObject);
        }
        jsonObject.add("roles", rolesArray);
        return jsonObject;
    }

    @Override
    public UserDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String email = jsonObject.get("email").getAsString();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        JsonArray rolesArray = jsonObject.get("roles").getAsJsonArray();
        List<RoleDTO> roles = new ArrayList<>();
        for (JsonElement roleElement : rolesArray) {
            JsonObject roleObject = roleElement.getAsJsonObject();
            int roleId = roleObject.get("id").getAsInt();
            String roleName = roleObject.get("name").getAsString();
            roles.add(new RoleDTO(roleId, roleName));
        }
        return new UserDTO(id, email, username, password, roles);
    }
}