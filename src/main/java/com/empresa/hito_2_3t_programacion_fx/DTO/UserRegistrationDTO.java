package com.empresa.hito_2_3t_programacion_fx.DTO;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private List<String> roles;
}