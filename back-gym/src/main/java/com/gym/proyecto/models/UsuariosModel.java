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
import lombok.Data;

@Entity
@Data
@Table(name="usuarios")
public class UsuariosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Esta vacio")
    private Long matricula;
    @NotEmpty(message = "Esta vacio")
    private String nombre;
    @NotEmpty(message = "Esta vacio")
    private String apePat;
    @NotEmpty(message = "Esta vacio")
    private String apeMat;
    @NotEmpty(message = "Esta vacio")
    @Email(message = "Es invalido")
    private String correo;
    @NotEmpty(message = "Esta vacio")
    private Long telefono;
    @NotEmpty(message = "Esta vacio")
    private Long telefonoEmer;
    @NotEmpty(message = "Esta vacio")
    private String foto;
    @NotEmpty(message = "Esta vacio")
    private Date fecha_registro;
    private Integer restraso_pago;
    private Date fecha_mensualidad;

    @ManyToOne
    @JoinColumn(name="estado")
    private EstadoUsuariosModel estado;

    @ManyToOne
    @JoinColumn(name="descuento")
    private DescuentoModel descuento;

    @ManyToOne
    @JoinColumn(name="tipo_pago")
    private TipoPagoModel tipoPago;

}
