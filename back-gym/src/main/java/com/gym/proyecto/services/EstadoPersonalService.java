package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.EstadoPersonalModel;
import com.gym.proyecto.repositorys.EstadoPersonalRepository;

@Service
public class EstadoPersonalService {

    @Autowired
    private EstadoPersonalRepository repository;

    public List<EstadoPersonalModel> listar(){
        return this.repository.findAll();
    }

    public EstadoPersonalModel buscarId(Integer id){
        return this.repository.findById(id).orElse(null);
    }
}
