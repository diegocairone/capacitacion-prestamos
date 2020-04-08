package com.eiv.das;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dao.ProvinciaDao;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.entities.QProvinciaEntity;
import com.eiv.interfaces.Provincia;
import com.eiv.stereotype.DataService;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@DataService
public class ProvinciaDas {

    @Autowired
    private ProvinciaDao provinciaDao;
    
    @Transactional(readOnly = true)
    public Optional<ProvinciaEntity> findById(Long id) {
        return provinciaDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ProvinciaEntity> findAll() {
        return provinciaDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<ProvinciaEntity> findAll(Function<QProvinciaEntity, BooleanExpression> function) {
        
        QProvinciaEntity provinciaQuery = QProvinciaEntity.provinciaEntity;
        BooleanExpression exp = function.apply(provinciaQuery);
        
        return (List<ProvinciaEntity>) provinciaDao.findAll(exp);
    }
    
    @Transactional
    public ProvinciaEntity save(Provincia provincia) {
        
        ProvinciaEntity provinciaEntity = new ProvinciaEntity();
        
        provinciaEntity.setNombre(provincia.getNombre());
        provinciaEntity.setRegion(provincia.getRegion());
        
        provinciaDao.save(provinciaEntity);
        
        return provinciaEntity;
    }

    @Transactional
    public ProvinciaEntity save(Long id, Provincia provincia) {
        
        ProvinciaEntity provinciaEntity = provinciaDao.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        provinciaEntity.setNombre(provincia.getNombre());
        provinciaEntity.setRegion(provincia.getRegion());
        
        provinciaDao.save(provinciaEntity);
        
        return provinciaEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        ProvinciaEntity provinciaEntity = provinciaDao.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        provinciaDao.delete(provinciaEntity);
    }

    private Supplier<? extends RuntimeException> exceptionSupplier(Long id) {
        return ExceptionUtils.notFoundExceptionSupplier(
                "NO EXISTE UNA PROVINCIA CON ID %s", id);
    }
}
