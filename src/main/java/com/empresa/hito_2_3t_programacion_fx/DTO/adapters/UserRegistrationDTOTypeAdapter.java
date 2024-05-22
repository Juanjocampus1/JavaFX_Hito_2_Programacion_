package com.empresa.hito_2_3t_programacion_fx.DTO.adapters;

import com.empresa.hito_2_3t_programacion_fx.DTO.UserRegistrationDTO;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserRegistrationDTOTypeAdapter extends TypeAdapter<UserRegistrationDTO> {

    @Override
    public void write(JsonWriter out, UserRegistrationDTO value) throws IOException {
        out.beginObject();
        out.name("username").value(value.getUsername());
        out.name("password").value(value.getPassword());
        out.name("email").value(value.getEmail());
        out.name("roles").beginArray();
        for (String role : value.getRoles()) {
            out.value(role);
        }
        out.endArray();
        out.endObject();
    }

    @Override
    public UserRegistrationDTO read(JsonReader in) throws IOException {
        // Implementación de la lectura de JSON a UserRegistrationDTO si es necesario
        return null;
    }
}