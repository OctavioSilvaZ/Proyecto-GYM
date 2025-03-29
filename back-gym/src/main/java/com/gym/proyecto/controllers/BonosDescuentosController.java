package com.gym.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.gym.proyecto.DTO.BonoIndividualRequest;
import com.gym.proyecto.models.BonosModel;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.services.BonosService;
import com.gym.proyecto.services.PersonalBonosService;
import com.gym.proyecto.services.PersonalService;
import com.gym.proyecto.utilidades.ResponseJson;

@RestController
@RequestMapping("gym/v1/admin")
public class BonosDescuentosController {

    @Autowired
    private BonosService bonosService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private PersonalBonosService personalBonosService;

    //Listar bonos
    @GetMapping("/bonos")
    public ResponseEntity<?> listarBonos(){
        return ResponseEntity.ok().body(this.bonosService.listar());
    }

    // Registrar un nuevo bono
    @PostMapping("/bonos/registrar")
    public ResponseEntity<?> bonoParaPersonal(@RequestBody BonosModel bono) {
        try {
            this.bonosService.guardar(bono);
            return ResponseJson.generateResponse(HttpStatus.OK, "Se registro el bono correctamente");

        } catch (Exception e) {
            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST,
                    "Ocurrio un error al registrar Bono");
        }

    }

    // Asignar bono a todo el personal
    @PutMapping("/bonos/empleados/{idBono}")
    public ResponseEntity<?> asginarBonoAlPersonal(@PathVariable("idBono") Long idBono) {
        BonosModel bono = this.bonosService.buscarporId(idBono);
        if (bono != null) {
            this.personalService.asignarBonoPersonal(bono.getId());
            return ResponseJson.generateResponse(HttpStatus.OK, "Se asigno el bono a todo el personal");
        } else {
            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, "No se encontro el bono asignado");
        }
    }


    //Asigna Bono individual
    @PutMapping("/bonos/individual")
    public ResponseEntity<?> bonoId(@RequestBody BonoIndividualRequest request) {

        final int ESTADO_ACTIVO = 1;
        PersonalModel personal = this.personalService.buscarPorId(request.getIdPersonal());
        BonosModel bono = this.bonosService.buscarporId(request.getIdBono());

        // Validar si existen
        if (personal == null || bono == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "El personal o el bono no existen");
        }

        if (personal.getEstado().getId() != ESTADO_ACTIVO) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND,
                    "No se puede asignar el bono al un personal inactivo");
        }

        if (this.personalBonosService.guardarBonoAsignado(personal, bono) == null) {
            return ResponseJson.generateResponse(HttpStatus.OK, "El bono ya estaba asignado");
        }
        return ResponseJson.generateResponse(HttpStatus.CREATED, "Se registro correctamente el bono al personal");
    }
}