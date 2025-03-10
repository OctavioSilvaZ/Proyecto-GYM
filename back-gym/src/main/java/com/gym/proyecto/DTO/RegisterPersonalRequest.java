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
    private int estado;
    private int horario;
    private int jornada;
}
