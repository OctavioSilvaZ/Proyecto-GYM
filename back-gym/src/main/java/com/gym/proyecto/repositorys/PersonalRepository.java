package com.gym.proyecto.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gym.proyecto.models.PersonalModel;

import jakarta.transaction.Transactional;


public interface PersonalRepository extends JpaRepository<PersonalModel, Long> {

    Optional<PersonalModel> findByCorreo(String correo);

@Modifying
@Transactional
    @Query(value = "INSERT INTO personal_bonos (personal_id, bono_id, fecha_asignacion, activo) " +
            "SELECT p.id, :bonoId, CURDATE(), 1 " +
            "FROM personal p " +
            "WHERE p.estado = 1 " +
            "AND NOT EXISTS (SELECT 1 FROM personal_bonos pb WHERE pb.personal_id = p.id AND pb.bono_id = :bonoId)", nativeQuery = true)
    void asignarBonoPersonal(@Param("bonoId") Long bonoId);
}

