package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gym.proyecto.models.RolPersonalModel;

public interface RolPersonalRepository extends JpaRepository<RolPersonalModel, Long> {

    RolPersonalModel findByPersonal_Id(Long personalId);

}
