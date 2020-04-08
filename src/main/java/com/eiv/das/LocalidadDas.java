package com.eiv.das;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dao.LocalidadDao;
import com.eiv.dao.ProvinciaDao;
import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.entities.QLocalidadEntity;
import com.eiv.exceptions.NotFoundServiceException;
import com.eiv.interfaces.Localidad;
import com.eiv.stereotype.DataService;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@DataService
public class LocalidadDas {

    @Autowired private LocalidadDao localidadDao;
    @Autowired private ProvinciaDao provinciaDao; 

    @Transactional(readOnly = true)
    public Optional<LocalidadEntity> findById(Long id) {
        return localidadDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LocalidadEntity> findAll(Function<QLocalidadEntity, BooleanExpression> function) {
        
        QLocalidadEntity localidadQuery = QLocalidadEntity.localidadEntity;
        BooleanExpression exp = function.apply(localidadQuery);
        
        return (List<LocalidadEntity>) localidadDao.findAll(exp);
    }

    @Transactional
    public LocalidadEntity save(Localidad localidad) {
        
        Long id = localidadDao.getMax().orElse(0L) + 1L;
        ProvinciaEntity provinciaEntity = provinciaDao
                .findById(localidad.getProvinciaId())
                .orElseThrow(() -> new NotFoundServiceException(
                        "NO SE ENCUENTRA UNA PROVINCIA CON ID %s", localidad.getProvinciaId()));
        
        LocalidadEntity localidadEntity = new LocalidadEntity();
        
        localidadEntity.setId(id);
        localidadEntity.setNombre(localidad.getNombre());
        localidadEntity.setProvincia(provinciaEntity);
        localidadEntity.setCodigoPostal(localidad.getCodigoPostal());
        
        localidadDao.save(localidadEntity);
        
        return localidadEntity;
    }

    @Transactional
    public LocalidadEntity save(Long id, Localidad localidad) {
        
        LocalidadEntity localidadEntity = localidadDao
                .findById(id)
                .orElseThrow(() -> new NotFoundServiceException(
                        "NO SE ENCUENTRA UNA LOCALIDAD CON ID %s", id));
        
        localidadEntity.setNombre(localidad.getNombre());
        localidadEntity.setCodigoPostal(localidad.getCodigoPostal());
        
        if (!localidad.getProvinciaId().equals(
                localidadEntity.getProvincia().getId())) {

            ProvinciaEntity provinciaEntity = provinciaDao
                    .findById(localidad.getProvinciaId())
                    .orElseThrow(() -> new NotFoundServiceException(
                            "NO SE ENCUENTRA UNA PROVINCIA CON ID %s", 
                            localidad.getProvinciaId()));
            
            localidadEntity.setProvincia(provinciaEntity);
        }
        
        localidadDao.save(localidadEntity);
        
        return localidadEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        LocalidadEntity localidadEntity = localidadDao.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        localidadDao.delete(localidadEntity);
    }

    private Supplier<? extends RuntimeException> exceptionSupplier(Long id) {
        return ExceptionUtils.notFoundExceptionSupplier(
                "NO EXISTE UNA LOCALIDAD CON ID %s", id);
    }
}
