package com.eiv.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dao.TipoDocumentoDao;
import com.eiv.entities.QTipoDocumentoEntity;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.interfaces.TipoDocumento;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public class TipoDocumentoRepository {

    @Autowired private TipoDocumentoDao tipoDocumentoDao;
    
    @Transactional(readOnly = true)
    public Optional<TipoDocumentoEntity> findById(Long id) {
        return tipoDocumentoDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<TipoDocumentoEntity> findAll() {
        return tipoDocumentoDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<TipoDocumentoEntity> findAll(
            Function<QTipoDocumentoEntity, BooleanExpression> function) {
        QTipoDocumentoEntity tipoDocumentoQuery = QTipoDocumentoEntity.tipoDocumentoEntity;
        BooleanExpression exp = function.apply(tipoDocumentoQuery);
        return (List<TipoDocumentoEntity>) tipoDocumentoDao.findAll(exp);
    }
    
    @Transactional
    public TipoDocumentoEntity save(TipoDocumento tipoDocumento) {
        
        TipoDocumentoEntity tipoDocumentoEntity = new TipoDocumentoEntity();
        tipoDocumentoEntity.setNombre(tipoDocumento.getNombre());
        tipoDocumentoEntity.setAbreviatura(tipoDocumento.getAbreviatura());
        tipoDocumentoEntity.setValidarComoCuit(tipoDocumento.getValidarComoCuit());
        
        tipoDocumentoDao.save(tipoDocumentoEntity);
        
        return tipoDocumentoEntity;
    }

    @Transactional
    public TipoDocumentoEntity save(Long id, TipoDocumento tipoDocumento) {

        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDao.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        tipoDocumentoEntity.setNombre(tipoDocumento.getNombre());
        tipoDocumentoEntity.setAbreviatura(tipoDocumento.getAbreviatura());
        tipoDocumentoEntity.setValidarComoCuit(tipoDocumento.getValidarComoCuit());
        
        tipoDocumentoDao.save(tipoDocumentoEntity);
        
        return tipoDocumentoEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDao.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        tipoDocumentoDao.delete(tipoDocumentoEntity);
    }

    private Supplier<? extends RuntimeException> exceptionSupplier(Long id) {
        return ExceptionUtils.notFoundExceptionSupplier(
                "NO EXISTE UN TIPO DE DOCUMENTO CON ID %s", id);
    }
}
