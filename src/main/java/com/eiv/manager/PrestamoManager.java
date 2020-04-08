package com.eiv.manager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.LineaEntity;
import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.enums.UnidadAmortizacionEnum;
import com.eiv.interfaces.Prestamo;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.interfaces.PrestamoSolicitudFrm;
import com.eiv.manager.task.PrestamoAltaTask;
import com.eiv.manager.task.PrestamoAmortizacionesTask;
import com.eiv.manager.task.PrestamoCuotaAltaTask;
import com.eiv.manager.task.PrestamoLineaTask;
import com.eiv.manager.task.PrestamoSumatoriaTask;
import com.eiv.manager.task.PrestamoTaskExecutor;
import com.eiv.repository.LineaRepository;
import com.eiv.repository.PrestamoCuotaRepository;
import com.eiv.repository.PrestamoRepository;

@Component
public class PrestamoManager {

    @Autowired private PrestamoRepository prestamoRepository;
    @Autowired private PrestamoCuotaRepository prestamoCuotaRepository;
    @Autowired private LineaRepository lineaRepository;
    
    @Transactional
    public PrestamoEntity manage(PrestamoSolicitudFrm solicitud, UsuarioEntity usuario) {
        
        final PrestamoTaskExecutor executor = new PrestamoTaskExecutor();
        
        PrestamoLineaTask lineaTask = PrestamoLineaTask.newInstance()
                .setLineaRepository(lineaRepository)
                .setLineaId(solicitud.getLineaId());
        
        LineaEntity lineaEntity = executor.run(lineaTask);
        
        PrestamoAmortizacionesTask amortizacionesTask = PrestamoAmortizacionesTask
                .newInstance()
                .setAmortizacionPeriodo(solicitud.getAmortizacionPeriodo())
                .setAmortizacionSistema(lineaEntity.getSistemaAmortizacion())
                .setAmortizacionUnidad(solicitud.getAmortizacionUnidad())
                .setCapitalPrestado(solicitud.getCapitalPrestado())
                .setFechaPrimerVto(solicitud.getFechaPrimerVto())
                .setTasaEfectiva(solicitud.getTasaEfectiva())
                .setTasaModulo(solicitud.getTasaModulo())
                .setTotalCuotas(solicitud.getTotalCuotas());
        
        List<PrestamoCuota> prestamoCuotas = executor.run(amortizacionesTask);

        PrestamoSumatoriaTask sumatoriaTask = PrestamoSumatoriaTask.newInstance()
                .setPrestamoCuotas(prestamoCuotas);
        
        BigDecimal totalIntereses = executor.run(sumatoriaTask);
        
        Prestamo prestamo = new Prestamo() {
            
            @Override
            public BigDecimal getTotalIntereses() {
                return totalIntereses;
            }
            
            @Override
            public Integer getTotalCuotas() {
                return solicitud.getTotalCuotas();
            }
            
            @Override
            public Integer getTasaModulo() {
                return solicitud.getTasaModulo();
            }
            
            @Override
            public BigDecimal getTasaEfectiva() {
                return solicitud.getTasaEfectiva();
            }
            
            @Override
            public Long getNumeroDocumento() {
                return solicitud.getNumeroDocumento();
            }
            
            @Override
            public Long getLineaId() {
                return solicitud.getLineaId();
            }
            
            @Override
            public LocalDate getFechaPrimerVto() {
                return solicitud.getFechaPrimerVto();
            }
            
            @Override
            public Long getDocumentoTipoId() {
                return solicitud.getDocumentoTipoId();
            }
            
            @Override
            public BigDecimal getCapitalPrestado() {
                return solicitud.getCapitalPrestado();
            }
            
            @Override
            public UnidadAmortizacionEnum getAmortizacionUnidad() {
                return solicitud.getAmortizacionUnidad();
            }
            
            @Override
            public Integer getAmortizacionPeriodo() {
                return solicitud.getAmortizacionPeriodo();
            }
        };
        
        PrestamoAltaTask altaTask = PrestamoAltaTask.newInstance()
                .setPrestamo(prestamo)
                .setUsuario(usuario)
                .setPrestamoRepository(prestamoRepository);
        
        PrestamoEntity prestamoEntity = executor.run(altaTask);
        
        prestamoCuotas.forEach(prestamoCuota -> {

            PrestamoCuotaAltaTask cuotaAltaTask = PrestamoCuotaAltaTask.newInstance()
                    .setPrestamoEntity(prestamoEntity)
                    .setPrestamoCuota(prestamoCuota)
                    .setPrestamoCuotaRepository(prestamoCuotaRepository);
                    
            executor.run(cuotaAltaTask);
        });
        
        return prestamoEntity;
    }
}
