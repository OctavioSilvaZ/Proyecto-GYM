package com.gym.proyecto.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.proyecto.DTO.LoginRequest;
import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.JWT.JwtService;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.services.AuthService;
import com.gym.proyecto.services.EstadoPersonalService;
import com.gym.proyecto.services.HorarioService;
import com.gym.proyecto.services.PersonalService;
import com.gym.proyecto.utilidades.ResponseJson;

import jakarta.validation.Valid;

@RestController
@RequestMapping("gym/auth")
public class AuthController {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jWTService;

    @Autowired
    private AuthService authService;

    @Autowired
    private EstadoPersonalService estadoPersonalService;

    @Autowired
    private HorarioService horarioService;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        PersonalModel personal = this.personalService.buscarPorCorreo(request.getCorreo());

        if (personal == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "Credenciales no validas");
        } else {
            if (this.passwordEncoder.matches(request.getPassword(), personal.getPassword())) {
                String token = this.jWTService.generateToken(personal.getCorreo());
                return ResponseEntity.ok(authService.login(token, personal));
            } else {
                return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, "Credenciales Invalidas");
            }
        }
    }

    @PostMapping("/registro/personal")
    public ResponseEntity<?> registroPersonal(@Valid @RequestBody RegisterPersonalRequest request) {

        PersonalModel personal = this.personalService.buscarPorCorreo(request.getCorreo());

        if (personal != null) {
            return ResponseJson.generateResponse(HttpStatus.CONFLICT, "El correo ya está registrado");
        } else {

            String nombre = request.getNombre().substring(0, 3);  // Primeras 3 letras del nombre
            String apePaterno = request.getApePaterno().substring(0, 4);  
            String apeMaterno = request.getApeMaterno().substring(0, 4);  

            String foto = nombre + "_" + apePaterno + "_" + apeMaterno;
            String ine = nombre + "_" + apePaterno + "_" + apeMaterno + "_Ine";
            PersonalModel nuevoPersonal = new PersonalModel(request.getNombre(), request.getApePaterno(),
             request.getApeMaterno(), request.getDireccion(), request.getTelefono(), request.getCorreo(),
              this.passwordEncoder.encode(request.getPassword()),
              foto, ine, new Date(System.currentTimeMillis()),
               this.estadoPersonalService.buscarId(request.getEstado()), this.horarioService.buscarId(request.getHorario()));
              this.personalService.guardar(nuevoPersonal, 2);
            
        return ResponseJson.generateResponseObject(HttpStatus.CREATED, nuevoPersonal);
        }

    }
}
