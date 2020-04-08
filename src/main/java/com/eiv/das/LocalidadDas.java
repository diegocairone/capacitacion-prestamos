package com.eiv.das;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.ProvinciaEntity;
import com.eiv.entities.QLocalidadEntity;
import com.eiv.exceptions.NotFoundServiceException;
import com.eiv.interfaces.Localidad;
import com.eiv.repository.LocalidadRepository;
import com.eiv.repository.ProvinciaRepository;
import com.eiv.stereotype.DataService;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@DataService
public class LocalidadDas {

    @Autowired private LocalidadRepository localidadRepository;
    @Autowired private ProvinciaRepository provinciaRepository; 

    @Transactional(readOnly = true)
    public Optional<LocalidadEntity> findById(Long id) {
        return localidadRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LocalidadEntity> findAll(Function<QLocalidadEntity, BooleanExpression> function) {
        
        QLocalidadEntity localidadQuery = QLocalidadEntity.localidadEntity;
        BooleanExpression exp = function.apply(localidadQuery);
        
        return (List<LocalidadEntity>) localidadRepository.findAll(exp);
    }

    @Transactional
    public LocalidadEntity save(Localidad localidad) {
        
        Long id = localidadRepository.getMax().orElse(0L) + 1L;
        ProvinciaEntity provinciaEntity = provinciaRepository
                .findById(localidad.getProvinciaId())
                .orElseThrow(() -> new NotFoundServiceException(
                        "NO SE ENCUENTRA UNA PROVINCIA CON ID %s", localidad.getProvinciaId()));
        
        LocalidadEntity localidadEntity = new LocalidadEntity();
        
        localidadEntity.setId(id);
        localidadEntity.setNombre(localidad.getNombre());
        localidadEntity.setProvincia(provinciaEntity);
        localidadEntity.setCodigoPostal(localidad.getCodigoPostal());
        
        localidadRepository.save(localidadEntity);
        
        return localidadEntity;
    }

    @Transactional
    public LocalidadEntity save(Long id, Localidad localidad) {
        
        LocalidadEntity localidadEntity = localidadRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundServiceException(
                        "NO SE ENCUENTRA UNA LOCALIDAD CON ID %s", id));
        
        localidadEntity.setNombre(localidad.getNombre());
        localidadEntity.setCodigoPostal(localidad.getCodigoPostal());
        
        if (!localidad.getProvinciaId().equals(
                localidadEntity.getProvincia().getId())) {

            ProvinciaEntity provinciaEntity = provinciaRepository
                    .findById(localidad.getProvinciaId())
                    .orElseThrow(() -> new NotFoundServiceException(
                            "NO SE ENCUENTRA UNA PROVINCIA CON ID %s", 
                            localidad.getProvinciaId()));
            
            localidadEntity.setProvincia(provinciaEntity);
        }
        
        localidadRepository.save(localidadEntity);
        
        return localidadEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        LocalidadEntity localidadEntity = localidadRepository.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        localidadRepository.delete(localidadEntity);
    }

    private Supplier<? extends RuntimeException> exceptionSupplier(Long id) {
        return ExceptionUtils.notFoundExceptionSupplier(
                "NO EXISTE UNA LOCALIDAD CON ID %s", id);
    }
}
