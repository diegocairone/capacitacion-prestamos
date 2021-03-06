package com.eiv.das;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.LineaEntity;
import com.eiv.entities.PersonaEntity;
import com.eiv.entities.PersonaPkEntity;
import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.QPrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.enums.TasaTipoEnum;
import com.eiv.enums.UnidadAmortizacionEnum;
import com.eiv.exceptions.DataIntegrityViolationServiceException;
import com.eiv.interfaces.Prestamo;
import com.eiv.maths.ctf.ConversorTasaFinanciera;
import com.eiv.maths.ctf.TasaFinanciera;
import com.eiv.maths.ctf.TipoTasaFinancieraEnum;
import com.eiv.repository.LineaRepository;
import com.eiv.repository.PersonaRepository;
import com.eiv.repository.PrestamoRepository;
import com.eiv.stereotype.DataService;
import com.eiv.utiles.ExceptionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@DataService
public class PrestamoDas {

    @Autowired private LineaRepository lineaRepository;
    @Autowired private PersonaRepository personaRepository;
    @Autowired private PrestamoRepository prestamoRepository;

    @Transactional(readOnly = true)
    public Optional<PrestamoEntity> findById(Long id) {
        return prestamoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<PrestamoEntity> findAll(Function<QPrestamoEntity, BooleanExpression> function) {
        
        QPrestamoEntity prestamoQuery = QPrestamoEntity.prestamoEntity;
        BooleanExpression exp = function.apply(prestamoQuery);
        
        return (List<PrestamoEntity>) prestamoRepository.findAll(exp);
    }
    
    @Transactional
    public PrestamoEntity nuevo(Prestamo prestamo, UsuarioEntity usuarioEntity) {
        
        final long id = prestamoRepository.getMax().orElse(0L) +  1L;
        
        final LineaEntity linea = lineaRepository.findById(prestamo.getLineaId()).orElseThrow(
                ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UNA LINEA CON ID %s", prestamo.getLineaId()));

        final PersonaEntity persona = personaRepository.findById(
                        new PersonaPkEntity(
                                prestamo.getDocumentoTipoId(), prestamo.getNumeroDocumento()))
                .orElseThrow(
                        ExceptionUtils.notFoundExceptionSupplier(
                                "NO EXISTE UNA LINEA CON ID %s", prestamo.getLineaId()));
        
        // *** VALIDACIONES RESPECTO DE LA LINEA DE PRESTAMO
        
        // VALIDACION: LA TASA DEL PRESTAMO ESTA EN EL RANGO ADMITIDO DE LA LINEA
        
        BigDecimal tasa = prestamo.getTasaEfectiva();
        long modulo = prestamo.getTasaModulo();
        long dias = prestamo.getAmortizacionUnidad().equals(UnidadAmortizacionEnum.DIA) 
                ? modulo : modulo * 30;
        
        if (linea.getTasaTipo().equals(TasaTipoEnum.NOMINAL)) {
            tasa = convertirTasaEfectivaEnNominal(tasa, modulo, dias);
        }
        
        if (tasa.compareTo(linea.getTasaMin()) == -1) {
            throw new DataIntegrityViolationServiceException(
                    "LA TASA [%s] ES MENOR A LA ADMITIDA EN LA LINEA: %s %s", 
                    tasa,
                    linea.getTasaMin(),
                    linea);
        }

        if (tasa.compareTo(linea.getTasaMax()) == 1) {
            throw new DataIntegrityViolationServiceException(
                    "LA TASA [%s] ES MAYOR A LA ADMITIDA EN LA LINEA: %s %s", 
                    tasa,
                    linea.getTasaMax(),
                    linea);
        }

        // VALIDACION: EL TOTAL DE CUOTAS DEL PRESTAMO ESTA EN EL RANGO ADMITIDO DE LA LINEA
        
        if (prestamo.getTotalCuotas().compareTo(linea.getCuotasMin()) < 0) {
            throw new DataIntegrityViolationServiceException(
                    "LA CANTIDAD TOTAL DE CUOTA [%s] ES MENOR A LA ADMITIDA EN LA LINEA: %s %s", 
                    prestamo.getTotalCuotas(),
                    linea.getCuotasMin(),
                    linea);
        }

        if (prestamo.getTotalCuotas().compareTo(linea.getCuotasMax()) > 0) {
            throw new DataIntegrityViolationServiceException(
                    "LA CANTIDAD TOTAL DE CUOTA [%s] ES MAYOR A LA ADMITIDA EN LA LINEA: %s %s", 
                    prestamo.getTotalCuotas(),
                    linea.getCuotasMax(),
                    linea);
        }

        // VALIDACION: EL TOTAL DE CAPITAL DEL PRESTAMO ESTA EN EL RANGO ADMITIDO DE LA LINEA
        
        if (prestamo.getCapitalPrestado().compareTo(linea.getCapitalMin()) == -1) {
            throw new DataIntegrityViolationServiceException(
                    "EL CAPITAL [%s] ES MENOR AL ADMITIDO EN LA LINEA: %s %s", 
                    prestamo.getCapitalPrestado(),
                    linea.getCapitalMin(),
                    linea);
        }

        if (prestamo.getCapitalPrestado().compareTo(linea.getCapitalMax()) == 1) {
            throw new DataIntegrityViolationServiceException(
                    "EL CAPITAL [%s] ES MAYOR AL ADMITIDO EN LA LINEA: %s %s", 
                    prestamo.getCapitalPrestado(),
                    linea.getCapitalMax(),
                    linea);
        }
        
        PrestamoEntity prestamoEntity = new PrestamoEntity();
        
        prestamoEntity.setId(id);
        prestamoEntity.setPersona(persona);
        prestamoEntity.setLinea(linea);
        prestamoEntity.setSistemaAmortizacion(linea.getSistemaAmortizacion());
        prestamoEntity.setFechaAlta(LocalDate.now());
        prestamoEntity.setFechaPrimerVto(prestamo.getFechaPrimerVto());
        prestamoEntity.setTasaEfectiva(prestamo.getTasaEfectiva());
        prestamoEntity.setTasaModulo(prestamo.getTasaModulo());
        prestamoEntity.setAmortizacionPeriodo(prestamo.getAmortizacionPeriodo());
        prestamoEntity.setAmortizacionUnidad(prestamo.getAmortizacionUnidad());
        prestamoEntity.setCapitalPrestado(prestamo.getCapitalPrestado());
        prestamoEntity.setTotalIntereses(prestamo.getTotalIntereses());
        prestamoEntity.setTotalCuotas(prestamo.getTotalCuotas());
        prestamoEntity.setUsuario(usuarioEntity);
                
        prestamoRepository.save(prestamoEntity);
        
        return prestamoEntity;
    }
    
    @Transactional
    public void borrar(Long prestamoId) {
        
        PrestamoEntity prestamoEntity = prestamoRepository.findById(prestamoId).orElseThrow(
                ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UN PRESTAMO CON ID %s", prestamoId));
        
        prestamoRepository.delete(prestamoEntity);
    }
    
    private BigDecimal convertirTasaEfectivaEnNominal(BigDecimal tasa, long modulo, long dias) {
        
        ConversorTasaFinanciera conversor = new ConversorTasaFinanciera();
        
        Optional<TasaFinanciera> resultado = conversor
                .datosIniciales(tf -> {
                    tf.setModulo(modulo);
                    tf.setTipo(TipoTasaFinancieraEnum.TE);
                    tf.setValor(tasa);
                }).convertir(TipoTasaFinancieraEnum.TNV, modulo, dias)
                .getResultado();
        
        return resultado.get().getValor();
    }
}
