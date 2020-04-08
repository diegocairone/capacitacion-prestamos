package com.eiv.das;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dao.LocalidadDao;
import com.eiv.dao.PersonaDao;
import com.eiv.dao.TipoDocumentoDao;
import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.PersonaEntity;
import com.eiv.entities.PersonaPkEntity;
import com.eiv.entities.QPersonaEntity;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.interfaces.Persona;
import com.eiv.stereotype.DataService;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@DataService
public class PersonaDas {

    @Autowired private LocalidadDao localidadDao;
    @Autowired private PersonaDao personaDao;
    @Autowired private TipoDocumentoDao tipoDocumentoDao;

    @Transactional(readOnly = true)
    public Optional<PersonaEntity> findById(Consumer<PersonaPkEntity> id) {
        PersonaPkEntity pk = new PersonaPkEntity();
        id.accept(pk);
        return personaDao.findById(pk);
    }

    @Transactional(readOnly = true)
    public List<PersonaEntity> findAll(Function<QPersonaEntity, BooleanExpression> function) {
        
        QPersonaEntity personaQuery = QPersonaEntity.personaEntity;
        BooleanExpression exp = function.apply(personaQuery);
        
        return (List<PersonaEntity>) personaDao.findAll(exp);
    }

    @Transactional
    public PersonaEntity save(Persona persona) {
        
        PersonaPkEntity pk = new PersonaPkEntity(
                persona.getTipoDocumentoId(), Long.valueOf(persona.getNumeroDocumento()));
        
        PersonaEntity personaEntity = personaDao.findById(pk).orElseGet(() -> {
            
            TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoDao
                    .findById(persona.getTipoDocumentoId())
                    .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                            "NO EXISTE UN TIPO DE DOCUMENTO CON ID %s", 
                            persona.getTipoDocumentoId()));
            
            PersonaEntity other = new PersonaEntity();
            other.setTipoDocumento(tipoDocumentoEntity);
            other.setNumeroDocumento(Long.valueOf(persona.getNumeroDocumento()));
            return other;
        });
        
        personaEntity.setNombreApellido(persona.getNombreApellido());
        personaEntity.setFechaNacimiento(persona.getFechaNacimiento());
        personaEntity.setGenero(persona.getGenero());
        personaEntity.setEsArgentino(persona.getEsArgentino());
        personaEntity.setEmail(persona.getEmail());
        personaEntity.setFoto(persona.getFoto());
        personaEntity.setCodigoPostal(persona.getCodigoPostal());
        
        if (personaEntity.getLocalidad() == null 
                || personaEntity.getLocalidad().getId().equals(persona.getLocalidadId())) {
            
            LocalidadEntity other = localidadDao.findById(persona.getLocalidadId())
                    .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                            "NO EXISTE UNA PERSONA CON ID %s", 
                            persona.getLocalidadId()));
            
            personaEntity.setLocalidad(other);
        }
        
        personaDao.save(personaEntity);
        
        return personaEntity;
    }

    @Transactional
    public void delete(Consumer<PersonaPkEntity> id) {
        
        PersonaPkEntity pk = new PersonaPkEntity();
        id.accept(pk);
        
        PersonaEntity personaEntity = personaDao.findById(pk)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UNA PERSONA CON ID %s", pk));
        
        personaDao.delete(personaEntity);
    }
}
