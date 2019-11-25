package com.eiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eiv.entities.PersonaEntity;
import com.eiv.entities.PersonaPkEntity;

public interface PersonaRepository extends JpaRepository<PersonaEntity, PersonaPkEntity> {

}
