package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.EstadoUsuariosModel;

public interface EstadoUsuariosRepository extends JpaRepository<EstadoUsuariosModel, Integer> {

}
