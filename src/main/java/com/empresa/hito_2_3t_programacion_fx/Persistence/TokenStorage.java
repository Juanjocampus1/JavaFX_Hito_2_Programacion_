package com.empresa.hito_2_3t_programacion_fx.Persistence;

public class TokenStorage {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TokenStorage.token = token;
    }
}