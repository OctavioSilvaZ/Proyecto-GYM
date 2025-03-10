package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;
import com.gym.proyecto.models.RolesModel;
import com.gym.proyecto.repositorys.RolPersonalRepository;

@Service
public class RolPersonalService {

    @Autowired
    private RolPersonalRepository repository;

    @Autowired
    private RolesService rolesService;

    public List<RolPersonalModel> listar(){
        return this.repository.findAll();
    }

    public RolPersonalModel buscarId(Long id){
        return this.repository.findById(id).orElse(null);
    }

    public RolPersonalModel buscarPersonalId(Long id){
        return this.repository.findByPersonal_Id(id);
    }

    public void guardar(PersonalModel personal,Integer rol){
        //Busca el rol por el id
        RolesModel rolFind =this.rolesService.buscarPorId(rol);

        if(rolFind!=null){
        // Crea el rol con id del rol y del personal
        RolPersonalModel rolPersona = new RolPersonalModel(personal, rolFind);
        this.repository.save(rolPersona);
        }
        else{
            throw new RuntimeException("Rol no encontrado");
        }
    }

    public void eliminarId(Long id){
        this.repository.deleteById(id);
    }


}
