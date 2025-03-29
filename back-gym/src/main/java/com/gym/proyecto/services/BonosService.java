package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.BonosModel;
import com.gym.proyecto.repositorys.BonosRepository;

@Service
public class BonosService {

    @Autowired
    private BonosRepository repository;

    public List<BonosModel> listar() {
        return this.repository.findAll();
    }

    public BonosModel guardar(BonosModel bono) {
        return this.repository.save(bono);
    }

    public void eliminarporId(Long id) {
        this.repository.deleteById(id);
    }

    public BonosModel buscarporId(Long id) {
        return this.repository.findById(id).orElse(null);
    }

}
