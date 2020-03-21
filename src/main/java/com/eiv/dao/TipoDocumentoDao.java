package com.eiv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.TipoDocumentoEntity;

public interface TipoDocumentoDao extends 
        JpaRepository<TipoDocumentoEntity, Long>, QuerydslPredicateExecutor<TipoDocumentoEntity> {

}
