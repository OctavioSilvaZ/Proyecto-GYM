package com.gym.proyecto.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.gym.proyecto.models.EstadoPersonalModel;
import com.gym.proyecto.models.HorarioModel;
import com.gym.proyecto.models.JornadaModel;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;
import com.gym.proyecto.services.AdminService;
import com.gym.proyecto.services.EstadoPersonalService;
import com.gym.proyecto.services.HorarioService;
import com.gym.proyecto.services.JornadaService;
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

    @Autowired
    private EstadoPersonalService estadosPersonalService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private JornadaService jornadaService;

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
    public ResponseEntity<?> personalFound(@PathVariable("id") Long id) {
        PersonalModel personal = this.personalService.buscarPorId(id);
        if (personal != null) {
            RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(personal.getId());
            if (rol.getRolId().getId() != 1) {
                return ResponseEntity.ok(this.adminService.personalFound(personal));
            } else {
                return ResponseJson.generateResponse(HttpStatus.UNAUTHORIZED,
                        "No estas autorizado para ver este contenido");
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

    // Obtener Foto de Personal
    @GetMapping("/personal/image/{id}")
    public ResponseEntity<?> updateImgPersonal(@PathVariable("id") Long id) {

        PersonalModel personal = this.personalService.buscarPorId(id);

        if (personal == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encontró al usuario");
        }

        try {
            // Ruta
            // FileSystemResource es para buscar carpetas en la raiz del proyecto o fuera
            Resource img = new FileSystemResource("personalFotos/" + personal.getFoto());

            if (!img.exists()) {
                return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "Imagen no encontrada");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(img);

        } catch (Exception e) {
            return ResponseJson.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error");
        }
    }

    // Obtener Estados
    @GetMapping("/personal/estados")

    public ResponseEntity<?> estadosList() {
        List<EstadoPersonalModel> estados = this.estadosPersonalService.listar();

        if (estados == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encotraron los estados");
        }

        return ResponseEntity.ok(estados);
    }

    // Buscar Estado
    @GetMapping("/personal/estados/{id}")

    public ResponseEntity<?> estadosFound(@Valid @PathVariable("id") Integer id) {
        EstadoPersonalModel estado = this.estadosPersonalService.buscarId(id);

        if (estado == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encotraron los estados");
        }

        return ResponseEntity.ok(estado);
    }

    // Obtener horarios
    @GetMapping("/personal/horarios")

    public ResponseEntity<?> horariosList() {
        List<HorarioModel> horarios = this.horarioService.listar();

        if (horarios == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encotraron los horarios");
        }

        return ResponseEntity.ok(horarios);
    }

    // Obtener jornadas
    @GetMapping("/personal/jornadas")

    public ResponseEntity<?> jornadasList() {
        List<JornadaModel> jornadas = this.jornadaService.listar();

        if (jornadas == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "No se encotraron las jornadas de trabajo");
        }

        return ResponseEntity.ok(jornadas);
    }

}
