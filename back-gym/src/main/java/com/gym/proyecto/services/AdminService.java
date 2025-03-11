package com.gym.proyecto.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gym.proyecto.DTO.PersonalDatosResponse;
import com.gym.proyecto.DTO.PersonalListResponse;
import com.gym.proyecto.DTO.RegisterPersonalRequest;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;

@Service
public class AdminService {

    @Autowired
    private RolPersonalService rolPersonalService;

    @Autowired 
    private PersonalService personalService;

   @Autowired
    private EstadoPersonalService estadoPersonalService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private JornadaService jornadaService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


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

    public PersonalModel personalUpdate(RegisterPersonalRequest request, long id){
        PersonalModel personal = this.personalService.buscarPorId(id);

        if(personal!=null){
        personal.setNombre(request.getNombre());
        personal.setApePaterno(request.getApePaterno());
        personal.setApeMaterno(request.getApeMaterno());
        personal.setDireccion(request.getDireccion());
        personal.setTelefono(request.getTelefono());
        personal.setCorreo(request.getCorreo());
        personal.setPassword(this.passwordEncoder.encode(request.getPassword()));
        personal.setEstado(this.estadoPersonalService.buscarId(request.getEstado()));
        personal.setHorario(this.horarioService.buscarId(request.getHorario()));
        personal.setJornada(this.jornadaService.buscarId(request.getJornada()));
        personal.setHoras_faltantes(request.getHorasExtra());
        personal.setHoras_extra(request.getHorasExtra());
        personal.setBono(request.getBono());
        personal.setNoCuenta(request.getNoCuenta());

        this.personalService.guardar(personal, 2);
       return personal;
        }else{
            return null;
        }
     }
}
