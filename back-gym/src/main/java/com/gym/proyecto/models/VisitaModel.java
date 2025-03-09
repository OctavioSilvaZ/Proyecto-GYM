package com.gym.proyecto.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name="visita")
public class VisitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Esta vacio")
    private String nombre;
    @NotEmpty(message = "Esta vacio")
    private String apePat;
    @NotEmpty(message = "Esta vacio")
    private String apeMat;
    private Date fecha;


    @ManyToOne
    @JoinColumn(name="monto")
    private MensualidadModel monto;

    @ManyToOne
    @JoinColumn(name="tipo_pago")
    private TipoPagoModel tipoPago;
}

