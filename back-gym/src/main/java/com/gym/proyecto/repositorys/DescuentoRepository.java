package com.gym.proyecto.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.proyecto.models.DescuentoModel;

public interface DescuentoRepository extends JpaRepository<DescuentoModel, Integer> {

}
