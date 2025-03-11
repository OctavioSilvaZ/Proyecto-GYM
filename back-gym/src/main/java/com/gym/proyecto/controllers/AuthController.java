package com.gym.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.proyecto.DTO.LoginRequest;
import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.JWT.JwtService;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.services.AuthService;
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

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request ) {

        PersonalModel personal = this.personalService.buscarPorCorreo(request.getCorreo());

        if (personal == null) {
            return ResponseJson.generateResponse(HttpStatus.NOT_FOUND, "Credenciales no validas");
        } else {
            if (this.passwordEncoder.matches(request.getPassword(), personal.getPassword())) {
                String token = this.jWTService.generateToken(personal.getCorreo());
                return ResponseEntity.ok(this.authService.login(token, personal));
            } else {
                return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, "Credenciales Invalidas");
            }
        }
    }

    @PostMapping("/registro/personal")
    public ResponseEntity<?> registroPersonal(@Valid @RequestBody RegisterPersonalRequest request,  BindingResult result) {

        return this.authService.registrarPersonal(request);
    }
}
