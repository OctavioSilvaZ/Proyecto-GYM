package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.RolesModel;
import com.gym.proyecto.repositorys.RolesRepository;

@Service
public class RolesService {

    @Autowired
    private RolesRepository repository;

    public List<RolesModel> listar(){
        return this.repository.findAll();
    }

    public RolesModel buscarPorId(Integer id){
        return this.repository.findById(id).orElse(null);
    }
}
