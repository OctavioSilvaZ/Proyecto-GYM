package com.gym.proyecto.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonalListResponse {
    private Long id;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String estado;
    private String horario;
    private String jornada;
    private LocalDate fechaPago;
}
