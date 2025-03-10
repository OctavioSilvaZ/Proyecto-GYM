package com.gym.proyecto.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.PersonalModel;

public interface PersonalRepository extends JpaRepository<PersonalModel, Long> {

    Optional<PersonalModel> findByCorreo(String correo);

}
 