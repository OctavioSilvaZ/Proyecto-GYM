package com.gym.proyecto.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "bonos")
public class BonosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "El nombre es obligatorio")
    String nombre;
    @NotNull
    Integer monto;
    @Column(updatable = false)
    LocalDate fecha;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDate.now();
    }

    //Indica que la relacion ya esta mapeada en
    @OneToMany(mappedBy = "bono")
     @JsonManagedReference // Esta es la referencia gestionada
    private List<PersonalBonosModel> empleadosAsignados;
}
