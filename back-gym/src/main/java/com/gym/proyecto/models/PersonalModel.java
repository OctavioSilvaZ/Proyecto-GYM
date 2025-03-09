package com.gym.proyecto.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name="personal")
public class PersonalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @NotEmpty(message = "Esta vacio")
    private String nombre;
    @NotEmpty(message = "Esta vacio")
    private String apePaterno;
    @NotEmpty(message = "Esta vacio")
    private String apeMaterno;
    @NotEmpty(message = "Esta vacio")
    private String direccion;
    @NotEmpty(message = "Esta vacio")
    private long telefono;
    @NotEmpty(message = "Esta vacio")
    @Email(message = "Es invalido")
    private String correo;
    @NotEmpty(message = "Esta vacio")
    private String password;
    @NotEmpty(message = "Esta vacio")
    private String foto;
    @NotEmpty(message = "Esta vacio")
    private String ine;
    @NotEmpty(message = "Esta vacio")
    private Date fecha_registro;
    private Integer horas_faltantes;
    private Integer horas_extra;
    private Integer dias_faltantes;
    private Integer bono;
    private Integer noCuenta;

    @ManyToOne
    @JoinColumn(name="estado")
    private EstadoPersonalModel estado;

    @ManyToOne
    @JoinColumn(name="horario")
    private HorarioModel horario;
}
