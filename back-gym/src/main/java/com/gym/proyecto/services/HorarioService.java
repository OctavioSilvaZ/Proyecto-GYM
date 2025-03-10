package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.HorarioModel;
import com.gym.proyecto.repositorys.HorarioRepository;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository repository;

    public List<HorarioModel> listar(){
        return this.repository.findAll();
    }

    public HorarioModel buscarId(Integer id){
        return this.repository.findById(id).orElse(null);
    }
}
