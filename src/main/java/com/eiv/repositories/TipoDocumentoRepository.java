package com.eiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.TipoDocumentoEntity;

public interface TipoDocumentoRepository extends 
        JpaRepository<TipoDocumentoEntity, Long>, QuerydslPredicateExecutor<TipoDocumentoEntity> {

}
