package com.empresa.hito_2_3t_programacion_fx.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDTO {
    private String username;
    private String password;
}
