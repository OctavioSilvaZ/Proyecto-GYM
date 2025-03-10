package com.gym.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;
import com.gym.proyecto.services.AdminService;
import com.gym.proyecto.services.PersonalService;
import com.gym.proyecto.services.RolPersonalService;
import com.gym.proyecto.utilidades.ResponseJson;

@RestController
@RequestMapping("/gym/v1/admin")
public class AdminCotroller {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private RolPersonalService rolPersonalService;

    @GetMapping("/personal")
    public ResponseEntity<?> personalList(){
        List<PersonalModel> personal = this.personalService.listar();
        return ResponseEntity.ok(this.adminService.personalAll(personal));
        }
    
    @GetMapping("/personal/{id}")
    public ResponseEntity<?> personalList(@PathVariable("id") Long id){
        PersonalModel personal = this.personalService.buscarPorId(id);
        if(personal!=null){
        RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(personal.getId());
        if(rol.getId()!=1){
        return ResponseEntity.ok(this.adminService.personalFound(personal));
        }
        else{
            return ResponseJson.generateResponse(HttpStatus.UNAUTHORIZED, "Ocurrio un error inesperado");
        }
        }
        else{
           return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encontro al personal");
        }
        }

}
