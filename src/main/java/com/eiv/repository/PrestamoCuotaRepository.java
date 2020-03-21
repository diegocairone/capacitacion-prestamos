package com.eiv.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dao.PrestamoCuotaDao;
import com.eiv.entities.PrestamoCuotaEntity;
import com.eiv.entities.PrestamoCuotaPkEntity;
import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.QPrestamoCuotaEntity;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public class PrestamoCuotaRepository {

    @Autowired private PrestamoCuotaDao prestamoCuotaDao;

    @Transactional(readOnly = true)
    public Optional<PrestamoCuotaEntity> findById(Consumer<PrestamoCuotaPkEntity> id) {
        PrestamoCuotaPkEntity pk = new PrestamoCuotaPkEntity();
        id.accept(pk);
        return prestamoCuotaDao.findById(pk);
    }

    @Transactional(readOnly = true)
    public List<PrestamoCuotaEntity> findAll(
            Function<QPrestamoCuotaEntity, BooleanExpression> function) {
        
        QPrestamoCuotaEntity prestamoCuotaQuery = QPrestamoCuotaEntity.prestamoCuotaEntity;
        BooleanExpression exp = function.apply(prestamoCuotaQuery);
        
        return (List<PrestamoCuotaEntity>) prestamoCuotaDao.findAll(exp);
    }

    @Transactional
    public PrestamoCuotaEntity nueva(PrestamoEntity prestamoEntity, PrestamoCuota prestamoCuota) {
        
        PrestamoCuotaEntity prestamoCuotaEntity = new PrestamoCuotaEntity();
        
        prestamoCuotaEntity.setPrestamo(prestamoEntity);
        prestamoCuotaEntity.setCuota(prestamoCuota.getCuota());
        prestamoCuotaEntity.setFechaVencimiento(prestamoCuota.getFechaVencimiento());
        prestamoCuotaEntity.setCapital(prestamoCuota.getCapital());
        prestamoCuotaEntity.setInteres(prestamoCuota.getInteres());
        prestamoCuotaEntity.setTotal(prestamoCuota.getCapital().add(prestamoCuota.getInteres()));
        prestamoCuotaEntity.setSaldoCapital(prestamoCuota.getSaldoCapital());
        
        prestamoCuotaDao.save(prestamoCuotaEntity);
        
        return prestamoCuotaEntity;
    }

    @Transactional
    public void borrar(Consumer<PrestamoCuotaPkEntity> id) {
        
        PrestamoCuotaPkEntity pk = new PrestamoCuotaPkEntity();
        id.accept(pk);
        
        PrestamoCuotaEntity prestamoCuotaEntity = prestamoCuotaDao.findById(pk).orElseThrow(
                ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UN PRESTAMO CON ID %s", pk));
        
        prestamoCuotaDao.delete(prestamoCuotaEntity);
    }
}
