package com.eiv.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.QTipoDocumentoEntity;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.interfaces.TipoDocumento;
import com.eiv.repositories.TipoDocumentoRepository;
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
        
//        tipoDocumentoEntity.setId(id);
        
        return tipoDocumentoEntity;
    }
}
