package com.gym.proyecto.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "personal")
public class PersonalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(updatable = false) // Evita que la fecha cambie después de la creación
    private LocalDate fecha_registro;
    private Integer horas_faltantes;
    private Integer horas_extra;
    private Integer dias_faltantes;
    private Integer noCuenta;
    private LocalDate fecha_pago;

    // Con esta notacion este metodo se ejecuatará antes de que
    // se guarde el objeto en la base de datos.
    @PrePersist
    protected void onCreate() {
        this.fecha_registro = LocalDate.now();
    }

    @ManyToOne
    @JoinColumn(name = "estado")
    @NotNull
    private EstadoPersonalModel estado;

    @ManyToOne
    @JoinColumn(name = "horario")
    @NotNull
    private HorarioModel horario;

    @ManyToOne
    @JoinColumn(name = "jornada_id")
    @NotNull
    private JornadaModel jornada;

    //Un personal puede tener varios bonos
    @OneToMany(mappedBy = "personal")
     @JsonManagedReference
    private List<PersonalBonosModel> bonosAsignados;

    
    //Cascada significa que cada accion del personal afectara a la tabla Personal_rol
    //Si se elimina un personal tambiens e eliminara su registro en la tabla Personal_rol
    //orphanRemoval = true
    //Si un PersonalModel pierde su referencia a RolPersonalModel, el rol se elimina automáticamente.
    @OneToOne(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private RolPersonalModel rolPersonal; 

    public PersonalModel() {
        super();
    }

    public PersonalModel(String nombre, String apePaterno, String apeMaterno, String direccion,
            String telefono, String correo, String password, String foto, String ine,
            EstadoPersonalModel estado, HorarioModel horario, JornadaModel jornada, LocalDate fecha_pago) {
        super();
        this.nombre = nombre;
        this.apePaterno = apePaterno;
        this.apeMaterno = apeMaterno;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
        this.foto = foto;
        this.ine = ine;
        this.estado = estado;
        this.horario = horario;
        this.jornada = jornada;
        this.fecha_pago = fecha_pago;
    }

}
