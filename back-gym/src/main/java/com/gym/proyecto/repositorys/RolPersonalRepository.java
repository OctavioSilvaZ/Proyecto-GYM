package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gym.proyecto.models.RolPersonalModel;

public interface RolPersonalRepository extends JpaRepository<RolPersonalModel, Long> {

    RolPersonalModel findByPersonal_Id(Long personalId);

    @Modifying
    @Transactional
    @Query("DELETE FROM RolPersonalModel r WHERE r.personal.id = :personalId")
    void deleteByPersonal_Id(@Param("personalId") Long personalId);

}
