package com.gym.proyecto.DTO;

import lombok.Data;

@Data
public class RegisterPersonalRequest {
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String direccion;
    private String telefono;
    private String correo;
    private String password;
    private Integer estado;
    private Integer horario;
    private Integer jornada;
    private Integer horasFaltantes;
    private Integer horasExtra;
    private Integer diasFaltantes;
    private Integer noCuenta;
}
