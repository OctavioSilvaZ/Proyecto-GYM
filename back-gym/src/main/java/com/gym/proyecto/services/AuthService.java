package com.gym.proyecto.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gym.proyecto.DTO.AuthResponse;
import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.JWT.JwtService;
import com.gym.proyecto.models.EstadoPersonalModel;
import com.gym.proyecto.models.HorarioModel;
import com.gym.proyecto.models.JornadaModel;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;
import com.gym.proyecto.utilidades.ResponseJson;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private RolPersonalService rolPersonalService;

    @Autowired
    private EstadoPersonalService estadoPersonalService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    JornadaService jornadaService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthResponse login(String token, PersonalModel personal) {

        RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(personal.getId());

        return new AuthResponse(token, personal.getNombre(), personal.getEstado().getNombre(),
                personal.getId(), rol.getRolId().getRol());
    }

    // Registro del nuevo personal
    public ResponseEntity<?> registrarPersonal(RegisterPersonalRequest request) {

        PersonalModel personal = this.personalService.buscarPorCorreo(request.getCorreo());

        if (personal != null) {
            return ResponseJson.generateResponse(HttpStatus.CONFLICT, "El correo ya está registrado");
        } else {

            EstadoPersonalModel estado = estadoPersonalService.buscarId(request.getEstado());
            HorarioModel horario = horarioService.buscarId(request.getHorario());
            JornadaModel jornada = jornadaService.buscarId(request.getJornada());

            if (estado == null || horario == null || jornada == null) {
                // Crear el mensaje de error dependiendo de qué valor es null
                String errorMessage = "";
                if (estado == null) {
                    errorMessage = "El estado no existe";
                } else if (horario == null) {
                    errorMessage = "El horario no existe";
                } else if (jornada == null) {
                    errorMessage = "La jornada no existe";
                }
                return ResponseJson.generateResponse(HttpStatus.BAD_REQUEST, errorMessage);
            }

            String nombre = request.getNombre().substring(0, 3); // Primeras 3 letras del nombre
            String apePaterno = request.getApePaterno().substring(0, 4);
            String apeMaterno = request.getApeMaterno().substring(0, 4);

            String foto = nombre + "_" + apePaterno + "_" + apeMaterno;
            String ine = nombre + "_" + apePaterno + "_" + apeMaterno + "_Ine";

            PersonalModel nuevoPersonal = new PersonalModel(
                    request.getNombre(), request.getApePaterno(), request.getApeMaterno(),
                    request.getDireccion(), request.getTelefono(), request.getCorreo(),
                    this.passwordEncoder.encode(request.getPassword()), foto, ine,
                    new Date(System.currentTimeMillis()),
                    estado,horario,jornada);

            this.personalService.guardar(nuevoPersonal, 2);

            return ResponseJson.generateResponseObject(HttpStatus.CREATED, nuevoPersonal);
        }
    }
}
