package com.eiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.PrestamoCuotaEntity;
import com.eiv.entities.PrestamoCuotaPkEntity;

public interface PrestamoCuotaRepository extends 
        JpaRepository<PrestamoCuotaEntity, PrestamoCuotaPkEntity>, 
        QuerydslPredicateExecutor<PrestamoCuotaEntity> {

}
