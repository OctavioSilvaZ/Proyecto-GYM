package com.gym.proyecto.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.DTO.PersonalDatosResponse;
import com.gym.proyecto.DTO.PersonalListResponse;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;

@Service
public class AdminService {

    @Autowired
    private RolPersonalService rolPersonalService;
    public PersonalDatosResponse personalFound(PersonalModel personal){

        PersonalDatosResponse personalResponse= new PersonalDatosResponse(personal.getId(), personal.getNombre(), personal.getApePaterno(), personal.getApeMaterno(),
        personal.getDireccion(), personal.getTelefono(), personal.getCorreo(),personal.getEstado().getNombre(), personal.getIne(),
        personal.getHorario().getTipo(),personal.getJornada().getTipo(), personal.getHoras_faltantes(),
        personal.getHoras_extra(),personal.getDias_faltantes(), personal.getBono(), personal.getNoCuenta());
    
        return personalResponse;
    }

    public List<PersonalListResponse> personalAll(List<PersonalModel> personal){

        List<PersonalListResponse>lista = new ArrayList<>();

       personal.forEach((datos)->{
        RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(datos.getId());
        if(rol.getId()!=1){
        lista.add(new PersonalListResponse(datos.getId(), datos.getNombre(), datos.getApePaterno(),
         datos.getApeMaterno(), datos.getEstado().getNombre()));
        }
       });

       return lista;
    }
}
