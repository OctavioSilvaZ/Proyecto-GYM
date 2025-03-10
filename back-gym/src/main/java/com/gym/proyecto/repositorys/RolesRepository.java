package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.RolesModel;

public interface RolesRepository extends JpaRepository<RolesModel, Integer> {
    RolesModel findByRol(String rol); 
}
