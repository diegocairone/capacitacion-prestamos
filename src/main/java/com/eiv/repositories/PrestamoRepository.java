package com.eiv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.PrestamoEntity;

public interface PrestamoRepository extends 
        JpaRepository<PrestamoEntity, Long>, QuerydslPredicateExecutor<PrestamoEntity> {

}
