package com.eiv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eiv.entities.ProvinciaEntity;

public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, Long> {

    @Query("SELECT MAX(e.id) FROM ProvinciaEntity e")
    public Optional<Long> getMax();
}
