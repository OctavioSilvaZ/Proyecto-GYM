package com.gym.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.repositorys.PersonalRepository;

@Service
public class PersonalService {

    @Autowired
    private PersonalRepository repository;

    @Autowired
    private RolPersonalService rolPersonalService;

    public List<PersonalModel> listar() {
        return this.repository.findAll();
    }

    public void guardar(PersonalModel personal, Integer rolId) {
        this.repository.save(personal);
        rolPersonalService.guardar(personal, rolId);
    }

    public PersonalModel buscarPorId(long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarPorID(long id) {
        this.rolPersonalService.eliminarPorPersonalId(id);
        this.repository.deleteById(id);
    }

    public PersonalModel buscarPorCorreo(String correo) {
        return this.repository.findByCorreo(correo).orElse(null);
    }

}
