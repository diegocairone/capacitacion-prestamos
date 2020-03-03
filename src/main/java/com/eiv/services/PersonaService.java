package com.eiv.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.LocalidadEntity;
import com.eiv.entities.PersonaEntity;
import com.eiv.entities.PersonaPkEntity;
import com.eiv.entities.QPersonaEntity;
import com.eiv.entities.TipoDocumentoEntity;
import com.eiv.interfaces.Persona;
import com.eiv.repositories.LocalidadRepository;
import com.eiv.repositories.PersonaRepository;
import com.eiv.repositories.TipoDocumentoRepository;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class PersonaService {

    @Autowired private LocalidadRepository localidadRepository;
    @Autowired private PersonaRepository personaRepository;
    @Autowired private TipoDocumentoRepository tipoDocumentoRepository;

    @Transactional(readOnly = true)
    public Optional<PersonaEntity> findById(Consumer<PersonaPkEntity> id) {
        PersonaPkEntity pk = new PersonaPkEntity();
        id.accept(pk);
        return personaRepository.findById(pk);
    }

    @Transactional(readOnly = true)
    public List<PersonaEntity> findAll(Function<QPersonaEntity, BooleanExpression> function) {
        
        QPersonaEntity personaQuery = QPersonaEntity.personaEntity;
        BooleanExpression exp = function.apply(personaQuery);
        
        return (List<PersonaEntity>) personaRepository.findAll(exp);
    }

    @Transactional
    public PersonaEntity save(Persona persona) {
        
        PersonaPkEntity pk = new PersonaPkEntity(
                persona.getTipoDocumentoId(), Long.valueOf(persona.getNumeroDocumento()));
        
        PersonaEntity personaEntity = personaRepository.findById(pk).orElseGet(() -> {
            
            TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoRepository
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
            
            LocalidadEntity other = localidadRepository.findById(persona.getLocalidadId())
                    .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                            "NO EXISTE UNA PERSONA CON ID %s", 
                            persona.getLocalidadId()));
            
            personaEntity.setLocalidad(other);
        }
        
        personaRepository.save(personaEntity);
        
        return personaEntity;
    }

    @Transactional
    public void delete(Consumer<PersonaPkEntity> id) {
        
        PersonaPkEntity pk = new PersonaPkEntity();
        id.accept(pk);
        
        PersonaEntity personaEntity = personaRepository.findById(pk)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UNA PERSONA CON ID %s", pk));
        
        personaRepository.delete(personaEntity);
    }
}
