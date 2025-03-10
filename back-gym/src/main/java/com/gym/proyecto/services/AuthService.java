package com.gym.proyecto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.DTO.AuthResponse;
import com.gym.proyecto.JWT.JwtService;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;

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

    public AuthResponse login(String token, PersonalModel personal) {

        RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(personal.getId());
        System.out.print("ESTE ES EL ID DEL ROL: " +rol.getRolId().getRol() );

        return new AuthResponse(token, personal.getNombre(), personal.getEstado().getNombre(), 
        personal.getId(), rol.getRolId().getRol());
    }

}
