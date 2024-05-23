package com.empresa.hito_2_3t_programacion_fx.DTO;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TokenDTO {
    private String token;

    public String getToken() {
        return token;
    }
}
