package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.VariableGlobalModel;
import com.gym.proyecto.repositorys.VariableGlobalRepository;

@Service
public class VariableGlobalService {

    @Autowired
    private VariableGlobalRepository repository;

    public List<VariableGlobalModel> listar(){
        return this.repository.findAll();
    }

    public void guardar(VariableGlobalModel model){
        this.repository.save(model);
    }

    public VariableGlobalModel buscarId(Integer id){
        return this.repository.findById(id).orElse(null);
    }

    public void eliminarId(Integer id){
        this.repository.deleteById(id);
    }
}
