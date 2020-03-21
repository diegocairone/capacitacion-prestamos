package com.eiv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.PersonaEntity;
import com.eiv.entities.PersonaPkEntity;

public interface PersonaDao extends 
        JpaRepository<PersonaEntity, PersonaPkEntity>, QuerydslPredicateExecutor<PersonaEntity> {

}
