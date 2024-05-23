package com.empresa.hito_2_3t_programacion_fx.DTO;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private int id;
    private String email;
    private String username;
    private String password;
    private List<RoleDTO> roles;
}
