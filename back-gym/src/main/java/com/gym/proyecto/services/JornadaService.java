package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.JornadaModel;
import com.gym.proyecto.repositorys.JornadaRepository;

@Service
public class JornadaService {

    @Autowired
    private JornadaRepository repository;

    public List<JornadaModel> listar(){
        return this.repository.findAll();
    }

    public JornadaModel buscarId(Integer id){
        return this.repository.findById(id).orElse(null);
    }
}
