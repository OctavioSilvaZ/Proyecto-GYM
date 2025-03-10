package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.HorarioModel;

public interface HorarioRepository extends JpaRepository<HorarioModel, Integer> {

}
