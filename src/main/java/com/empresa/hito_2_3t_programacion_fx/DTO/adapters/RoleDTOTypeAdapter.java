package com.empresa.hito_2_3t_programacion_fx.DTO.adapters;

import com.empresa.hito_2_3t_programacion_fx.DTO.RoleDTO;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RoleDTOTypeAdapter implements JsonSerializer<RoleDTO>, JsonDeserializer<RoleDTO> {

    @Override
    public JsonElement serialize(RoleDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        return jsonObject;
    }

    @Override
    public RoleDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        return new RoleDTO(id, name);
    }
}