package com.empresa.hito_2_3t_programacion_fx.DTO;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class LoginDTOTypeAdapter extends TypeAdapter<LoginDTO> {

    @Override
    public void write(JsonWriter out, LoginDTO value) throws IOException {
        out.beginObject();
        out.name("username").value(value.getUsername());
        out.name("password").value(value.getPassword());
        out.endObject();
    }

    @Override
    public LoginDTO read(JsonReader in) throws IOException {
        LoginDTO loginDTO = new LoginDTO();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "username":
                    loginDTO.setUsername(in.nextString());
                    break;
                case "password":
                    loginDTO.setPassword(in.nextString());
                    break;
            }
        }
        in.endObject();
        return loginDTO;
    }
}