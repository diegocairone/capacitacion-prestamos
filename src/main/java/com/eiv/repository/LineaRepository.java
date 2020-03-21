package com.eiv.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.dao.LineaDao;
import com.eiv.entities.LineaEntity;
import com.eiv.entities.QLineaEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.interfaces.Linea;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public class LineaRepository {

    @Autowired private LineaDao lineaRepository;

    @Transactional(readOnly = true)
    public Optional<LineaEntity> findById(Long id) {
        return lineaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LineaEntity> findAll(Function<QLineaEntity, BooleanExpression> function) {
        
        QLineaEntity lineaQuery = QLineaEntity.lineaEntity;
        BooleanExpression exp = function.apply(lineaQuery);
        
        return (List<LineaEntity>) lineaRepository.findAll(exp);
    }

    @Transactional
    public LineaEntity save(Linea linea, UsuarioEntity usuario) {
        
        Long id = lineaRepository.getMax().orElse(0L) + 1L;
        
        LineaEntity lineaEntity = new LineaEntity();
        
        lineaEntity.setId(id);
        lineaEntity.setNombre(linea.getNombre());
        lineaEntity.setTasaTipo(linea.getTasaTipo());
        lineaEntity.setTasaModulo(linea.getTasaModulo());
        lineaEntity.setAmortizacionPeriodo(linea.getAmortizacionPeriodo());
        lineaEntity.setAmortizacionUnidad(linea.getAmortizacionUnidad());
        lineaEntity.setSistemaAmortizacion(linea.getSistemaAmortizacion());
        lineaEntity.setTasaMin(linea.getTasaMin());
        lineaEntity.setTasaMax(linea.getTasaMax());
        lineaEntity.setCuotasMin(linea.getCuotasMin());
        lineaEntity.setCuotasMax(linea.getCuotasMax());
        lineaEntity.setCapitalMin(linea.getCapitalMin());
        lineaEntity.setCapitalMax(linea.getCapitalMax());
        lineaEntity.setFechaAlta(LocalDate.now());
        lineaEntity.setUsuario(usuario);
        
        lineaRepository.save(lineaEntity);
        
        return lineaEntity;
    }

    @Transactional
    public LineaEntity save(Long id, Linea linea) {
        
        LineaEntity lineaEntity = lineaRepository
                .findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        lineaEntity.setNombre(linea.getNombre());
        lineaEntity.setTasaTipo(linea.getTasaTipo());
        lineaEntity.setTasaModulo(linea.getTasaModulo());
        lineaEntity.setAmortizacionPeriodo(linea.getAmortizacionPeriodo());
        lineaEntity.setAmortizacionUnidad(linea.getAmortizacionUnidad());
        lineaEntity.setSistemaAmortizacion(linea.getSistemaAmortizacion());
        lineaEntity.setTasaMin(linea.getTasaMin());
        lineaEntity.setTasaMax(linea.getTasaMax());
        lineaEntity.setCuotasMin(linea.getCuotasMin());
        lineaEntity.setCuotasMax(linea.getCuotasMax());
        lineaEntity.setCapitalMin(linea.getCapitalMin());
        lineaEntity.setCapitalMax(linea.getCapitalMax());

        lineaRepository.save(lineaEntity);
        
        return lineaEntity;
    }

    @Transactional
    public void delete(Long id) {
        
        LineaEntity lineaEntity = lineaRepository.findById(id)
                .orElseThrow(exceptionSupplier(id));
        
        lineaRepository.delete(lineaEntity);
    }

    private Supplier<? extends RuntimeException> exceptionSupplier(Long id) {
        return ExceptionUtils.notFoundExceptionSupplier(
                "NO EXISTE UNA LINEA CON ID %s", id);
    }
}
