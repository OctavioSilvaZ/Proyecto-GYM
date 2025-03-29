package com.gym.proyecto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="personal_bonos")
public class PersonalBonosModel {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private Integer activo;

    @ManyToOne
    @JoinColumn(name="personal_id")
    @JsonBackReference // Esta es la referencia inversa
    private PersonalModel personal;

    @ManyToOne
    @JoinColumn(name="bono_id")
    @JsonBackReference // Esta es la referencia inversa
    private BonosModel bono;

    public PersonalBonosModel (){
        super();
    }

    public PersonalBonosModel(PersonalModel personal, BonosModel bono){
        this.personal = personal;
        this.bono = bono;
        this.activo=1;
    }


}
