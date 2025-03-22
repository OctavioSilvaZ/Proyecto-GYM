package com.gym.proyecto.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonalDatosResponse {
    private Long id;
    private String nombre;
    private String apePaterno;
    private String apeMaterno;
    private String direccion;
    private String telefono;
    private String correo;
    private String estado;
    private Integer estado_id;
    private String ine;
    private String horario;
    private Integer horario_id;
    private String jornada;
    private Integer jornada_id;
    private Integer horas_Faltantes;
    private Integer horas_Extra;
    private Integer dias_Faltantes;
    private Integer bono;
    private Integer noCuenta;
    private LocalDate fecha_pago;
}
