package com.gym.proyecto.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="roles")
public class RolesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String rol;

    //Retorna una lista de todos los empleados que tengan ese rol
    @OneToMany(mappedBy = "rolId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RolPersonalModel> empleadosAsignados;
}
