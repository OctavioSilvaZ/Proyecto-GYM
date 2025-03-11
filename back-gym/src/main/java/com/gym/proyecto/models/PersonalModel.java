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
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
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
    @NotNull
    private String telefono;
    @NotEmpty(message = "Esta vacio")
    @Email(message = "Es invalido")
    private String correo;
    @NotEmpty(message = "Esta vacio")
    private String password;
    private String foto;
    private String ine;
    private Date fecha_registro;
    private Integer horas_faltantes;
    private Integer horas_extra;
    private Integer dias_faltantes;
    private Integer bono;
    private Integer noCuenta;

    @ManyToOne
    @JoinColumn(name="estado")
    @NotNull
    private EstadoPersonalModel estado;

    @ManyToOne
    @JoinColumn(name="horario")
    @NotNull
    private HorarioModel horario;

    @ManyToOne
    @JoinColumn(name="jornada_id")
    @NotNull
    private JornadaModel jornada;

    public PersonalModel(){
        super();
    }

    public PersonalModel(String nombre,String apePaterno,String apeMaterno,String direccion,
    String telefono,String correo,String password,String foto,String ine, Date fecha_registro,
    EstadoPersonalModel estado, HorarioModel horario, JornadaModel jornada){
        super();
        this.nombre=nombre;
        this.apePaterno=apePaterno;
        this.apeMaterno=apeMaterno;
        this.direccion=direccion;
        this.telefono=telefono;
        this.correo=correo;
        this.password=password;
        this.foto=foto;
        this.ine=ine;
        this.fecha_registro=fecha_registro;
        this.estado=estado;
        this.horario=horario;
        this.jornada = jornada;
    }

}
