package com.eiv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.LocalidadEntity;

public interface LocalidadRepository extends 
        JpaRepository<LocalidadEntity, Long>, QuerydslPredicateExecutor<LocalidadEntity> {

    @Query("SELECT MAX(e.id) FROM LocalidadEntity e")
    public Optional<Long> getMax();
}
