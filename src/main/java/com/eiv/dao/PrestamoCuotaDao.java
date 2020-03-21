package com.eiv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.eiv.entities.PrestamoCuotaEntity;
import com.eiv.entities.PrestamoCuotaPkEntity;

public interface PrestamoCuotaDao extends 
        JpaRepository<PrestamoCuotaEntity, PrestamoCuotaPkEntity>, 
        QuerydslPredicateExecutor<PrestamoCuotaEntity> {

}
