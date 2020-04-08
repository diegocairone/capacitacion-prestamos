package com.eiv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.LineaEntity;

public interface LineaRepository extends 
        JpaRepository<LineaEntity, Long>, QuerydslPredicateExecutor<LineaEntity> {

    @Query("SELECT MAX(e.id) FROM LineaEntity e")
    public Optional<Long> getMax();
}
