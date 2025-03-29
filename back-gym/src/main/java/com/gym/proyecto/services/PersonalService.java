package com.gym.proyecto.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.gym.proyecto.models.BonosModel;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.repositorys.PersonalRepository;

@Service
public class PersonalService {

    private static final String FolderFotos = "personalFotos";
    private static final String FolderIne = "personalIne";
    private static final String FolderFotosUsers = "usuariosFotos";

    @Autowired
    private PersonalRepository repository;

    @Autowired
    private RolPersonalService rolPersonalService;

    @Autowired
    private BonosService bonosService;



    public List<PersonalModel> listar() {
        return this.repository.findAll();
    }

    public void guardarArchivo(MultipartFile file, int tipo, String nombre) throws IOException {
        try {
            // Foto
            if (tipo == 0) {
                Path filePath = Paths.get(FolderFotos, nombre);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Ine
            if (tipo == 1) {
                Path filePath = Paths.get(FolderIne, nombre);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Foto de usuarios
            if (tipo == 3) {
                Path filePath = Paths.get(FolderFotosUsers, nombre);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            }
        } catch (IOException e) {
            throw new IOException("No se pudo guardar la imagen: " + e.getMessage());
        }

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
        this.repository.deleteById(id);
    }

    public PersonalModel buscarPorCorreo(String correo) {
        return this.repository.findByCorreo(correo).orElse(null);
    }

    public void asignarBonoPersonal(long idBono) {
        BonosModel bono = this.bonosService.buscarporId(idBono);
        if (bono == null) {
            throw new RuntimeException("No se encontró el bono");
        }
        
        this.repository.asignarBonoPersonal(bono.getId());
    }

}
