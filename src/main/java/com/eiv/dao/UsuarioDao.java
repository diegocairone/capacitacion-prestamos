package com.eiv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.PersonaPkEntity;
import com.eiv.entities.UsuarioEntity;

public interface UsuarioDao extends 
        JpaRepository<UsuarioEntity, PersonaPkEntity>, QuerydslPredicateExecutor<UsuarioEntity> {

}
