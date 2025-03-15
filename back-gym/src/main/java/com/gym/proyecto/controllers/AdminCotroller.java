package com.gym.proyecto.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;
import com.gym.proyecto.services.AdminService;
import com.gym.proyecto.services.PersonalService;
import com.gym.proyecto.services.RolPersonalService;
import com.gym.proyecto.utilidades.ResponseJson;

import jakarta.validation.Valid;

@RestController
@RequestMapping("gym/v1/admin")
public class AdminCotroller {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private RolPersonalService rolPersonalService;

    // Listar Personal
    @GetMapping("/personal")
    public ResponseEntity<?> personalList() {
        List<PersonalModel> personal = this.personalService.listar();
        if (personal.isEmpty()) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "Ocurrio un error al listar personal");
        }
        return ResponseEntity.ok(this.adminService.personalAll(personal));
    }

    // Buscar personal
    @GetMapping("/personal/{id}")
    public ResponseEntity<?> personalList(@PathVariable("id") Long id) {
        PersonalModel personal = this.personalService.buscarPorId(id);
        if (personal != null) {
            RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(personal.getId());
            if (rol.getRolId().getId() != 1) {
                return ResponseEntity.ok(this.adminService.personalFound(personal));
            } else {
                return ResponseJson.generateResponse(HttpStatus.UNAUTHORIZED, "Ocurrio un error inesperado");
            }
        } else {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encontro al personal");
        }
    }

    // Actualizar personal
    @PutMapping("/personal/{id}")
    public ResponseEntity<?> personalUpdate(@Valid @PathVariable("id") Long id,
            RegisterPersonalRequest request,
            @RequestParam(value = "foto", required = false) MultipartFile foto,
            @RequestParam(value = "ine", required = false) MultipartFile ine) throws IOException {

        PersonalModel correo = this.personalService.buscarPorCorreo(request.getCorreo());
        PersonalModel personalUpdate = this.personalService.buscarPorId(id);

        String fotoTipo = foto.getContentType(); // obtiene los datos de la foto
        String ineTipo = ine.getContentType(); // Obtiene los datos del ine

        if (fotoTipo != null && !fotoTipo.equals("image/jpeg")
                && !fotoTipo.equals("image/png")) {

            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST,
                    "La foto debe ser de tipo JPG o PNG.");
        }

        if (ineTipo != null && !ineTipo.equals("application/pdf")) {
            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, "El INE debe ser de tipo PDF.");
        }

        if (personalUpdate == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encontró al usuario");
        }

        RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(personalUpdate.getId());
        if (rol.getRolId().getId() == 1) {
            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST,
                    "No tienes permisos para actualizar este usuario");
        }

        if (correo != null && !correo.getId().equals(id)) {
            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, "El correo ya está registrado");
        }
        this.adminService.personalUpdate(request, personalUpdate.getId(), foto, ine);
        return ResponseJson.generateResponse(HttpStatus.OK, "Se actualizó correctamente al usuario");
    }

    // Eliminar personal
    @DeleteMapping("/personal/{id}")
    public ResponseEntity<?> deletePersonal(@PathVariable("id") Long id) {
        PersonalModel personal = this.personalService.buscarPorId(id);

        if (personal == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND,
                    "No se encontró al usuario");
        } else {
            this.personalService.eliminarPorID(id);
            return ResponseJson.generateResponse(HttpStatus.OK, "Se elimino correctamente al usuario");
        }

    }

}
