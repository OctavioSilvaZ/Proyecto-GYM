package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.UsuariosModel;

public interface UsuariosRepository extends JpaRepository<UsuariosModel,Long> {

}
