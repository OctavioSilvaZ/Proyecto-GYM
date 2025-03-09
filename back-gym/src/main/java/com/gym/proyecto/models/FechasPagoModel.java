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
@Table(name="fechas_pagos")
public class FechasPagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha;
    @NotEmpty(message = "Esta vacio")
    private Integer monto;

    @ManyToOne
    @JoinColumn(name = "id_users", nullable = false)
    private PersonalModel idUser;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", nullable = false)
    private TipoPagoModel tipoPagoId;
}
