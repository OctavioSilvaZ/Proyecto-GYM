package com.gym.proyecto.DTO;

import java.time.LocalDate;
import java.util.List;

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
    private List<Integer> bono;
    private List<String> bono_nombre;
}
