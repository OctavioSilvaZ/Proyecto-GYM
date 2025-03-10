package com.gym.proyecto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonalListResponse {
    private Long id;
    private String nombre;
    private String apeParterno;
    private String apeMaterno;
    private String estado;
}
