package com.gym.proyecto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    String token;
    String nombre;
    String estado;
    Long id;
    String rol;
}
