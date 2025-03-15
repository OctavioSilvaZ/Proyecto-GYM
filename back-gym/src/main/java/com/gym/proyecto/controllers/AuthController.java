package com.gym.proyecto.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

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

    // Registrar personal
    @PostMapping("/registro/personal")
    public ResponseEntity<?> registroPersonal(@Valid RegisterPersonalRequest request,
            @RequestParam(value = "foto", required = false) MultipartFile foto,
            @RequestParam(value = "ine", required = false) MultipartFile ine) throws IOException {

        String fotoTipo = foto.getContentType(); // obtiene los datos de la foto
        String ineTipo = ine.getContentType(); // Obtiene los datos del ine

        // verifica que la foto tenga extensión .jpg o .png
        if (fotoTipo != null && !fotoTipo.equals("image/jpeg")
                && !fotoTipo.equals("image/png")) {

            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST,
                    "La foto debe ser de tipo JPG o PNG.");
        }

        // Verifica que el ine tenga la extension .pdf
        if (ineTipo != null && !ineTipo.equals("application/pdf")) {
            return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, "El INE debe ser de tipo PDF.");
        }

        return this.authService.registrarPersonal(request, foto, ine);
    }
}
