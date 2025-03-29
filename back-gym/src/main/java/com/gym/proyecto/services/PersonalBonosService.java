package com.gym.proyecto.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.proyecto.models.BonosModel;
import com.gym.proyecto.models.PersonalBonosModel;
import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.repositorys.PersonalBonosRepository;


@Service
public class PersonalBonosService {

  @Autowired
  private PersonalBonosRepository repository;

  public PersonalBonosModel buscarBonoAsiando(PersonalModel personal, BonosModel bono){
    return this.repository.findByPersonalIdAndBonoIdAndActivo(personal.getId(), bono.getId(),1).orElse(null);
  }

  public PersonalBonosModel guardarBonoAsignado(PersonalModel personal, BonosModel bono){
    PersonalBonosModel bonofound = this.buscarBonoAsiando(personal, bono);

    if(bonofound == null){
      PersonalBonosModel nuevoBono = new PersonalBonosModel(personal, bono);
      return this.repository.save(nuevoBono);
    }
    else{
      return null;
    }
  }


    

}
