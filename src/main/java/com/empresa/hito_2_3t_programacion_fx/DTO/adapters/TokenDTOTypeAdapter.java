package com.empresa.hito_2_3t_programacion_fx.DTO.adapters;

import com.empresa.hito_2_3t_programacion_fx.DTO.TokenDTO;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TokenDTOTypeAdapter implements JsonSerializer<TokenDTO>, JsonDeserializer<TokenDTO> {

    @Override
    public JsonElement serialize(TokenDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", src.getToken());
        return jsonObject;
    }

    @Override
    public TokenDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String token = jsonObject.get("token").getAsString();
        return new TokenDTO(token);
    }
}
