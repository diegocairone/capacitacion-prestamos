package com.eiv.services.dal;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.QTipoDocumentoEntity;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.interfaces.TipoDocumento;
import com.eiv.repositories.TipoDocumentoRepository;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class TipoDocumentoService {

    @Autowired private TipoDocumentoRepository tipoDocumentoRepository;
    
    @Transactional(readOnly = true)
    public Optional<TipoDocumentoEntity> findById(Long id) {
        return tipoDocumentoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<TipoDocumentoEntity> findAll() {
        return tipoDocumentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TipoDocumentoEntity> findAll(
            Function<QTipoDocumentoEntity, BooleanExpression> function) {
        QTipoDocumentoEntity tipoDocumentoQuery = QTipoDocumentoEntity.tipoDocumentoEntity;
        BooleanExpression exp = function.apply(tipoDocumentoQuery);
        return (List<TipoDocumentoEntity>) tipoDocumentoRepository.findAll(exp);
    }
    
    @Transactional
    public TipoDocumentoEntity save(TipoDocumento tipoDocumento) {
        
        TipoDocumentoEntity tipoDocumentoEntity = new TipoDocumentoEntity();
        tipoDocumentoEntity.setNombre(tipoDocumento.getNombre());
        tipoDocumentoEntity.setAbreviatura(tipoDocumento.getAbreviatura());
        tipoDocumentoEntity.setValidarComoCuit(tipoDocumento.getValidarComoCuit());
        
        tipoDocumentoRepository.save(tipoDocumentoEntity);
        
        return tipoDocumentoEntity;
    }

    @Transactional
    public TipoDocumentoEntity save(Long id, TipoDocumento tipoDocumento) {

        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoRepository.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        tipoDocumentoEntity.setNombre(tipoDocumento.getNombre());
        tipoDocumentoEntity.setAbreviatura(tipoDocumento.getAbreviatura());
        tipoDocumentoEntity.setValidarComoCuit(tipoDocumento.getValidarComoCuit());
        
        tipoDocumentoRepository.save(tipoDocumentoEntity);
        
        return tipoDocumentoEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoRepository.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        tipoDocumentoRepository.delete(tipoDocumentoEntity);
    }

    private Supplier<? extends RuntimeException> exceptionSupplier(Long id) {
        return ExceptionUtils.notFoundExceptionSupplier(
                "NO EXISTE UN TIPO DE DOCUMENTO CON ID %s", id);
    }
}
