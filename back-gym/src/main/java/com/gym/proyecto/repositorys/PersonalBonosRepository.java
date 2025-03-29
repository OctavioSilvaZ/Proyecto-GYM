package com.gym.proyecto.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.PersonalBonosModel;



public interface PersonalBonosRepository extends JpaRepository<PersonalBonosModel, Long> {

  Optional<PersonalBonosModel> findByPersonalIdAndBonoIdAndActivo(Long personalId, Long bonoId, Integer activo);
}
