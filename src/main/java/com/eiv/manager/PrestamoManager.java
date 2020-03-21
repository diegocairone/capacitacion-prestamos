package com.eiv.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.interfaces.Prestamo;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.manager.task.PrestamoAltaTask;
import com.eiv.manager.task.PrestamoAmortizacionesTask;
import com.eiv.manager.task.PrestamoCuotaAltaTask;
import com.eiv.manager.task.PrestamoSumatoriaTask;
import com.eiv.manager.task.PrestamoTaskExecutor;
import com.eiv.repository.PrestamoCuotaRepository;
import com.eiv.repository.PrestamoRepository;

@Component
public class PrestamoManager {

    @Autowired private PrestamoRepository prestamoRepository;
    @Autowired private PrestamoCuotaRepository prestamoCuotaRepository;
    
    @Transactional
    public PrestamoEntity manage(Prestamo prestamo, UsuarioEntity usuario) {
        
        final PrestamoTaskExecutor executor = new PrestamoTaskExecutor();
        
        PrestamoAltaTask altaTask = PrestamoAltaTask.newInstance()
                .setPrestamo(prestamo)
                .setUsuario(usuario)
                .setPrestamoRepository(prestamoRepository);
        
        PrestamoEntity prestamoEntity = executor.run(altaTask);
        
        PrestamoAmortizacionesTask amortizacionesTask = PrestamoAmortizacionesTask
                .newInstance()
                .setAmortizacionPeriodo(prestamoEntity.getAmortizacionPeriodo())
                .setAmortizacionSistema(prestamoEntity.getSistemaAmortizacion())
                .setAmortizacionUnidad(prestamoEntity.getAmortizacionUnidad())
                .setCapitalPrestado(prestamoEntity.getCapitalPrestado())
                .setFechaPrimerVto(prestamoEntity.getFechaPrimerVto())
                .setTasaEfectiva(prestamoEntity.getTasaEfectiva())
                .setTasaModulo(prestamoEntity.getTasaModulo())
                .setTotalCuotas(prestamoEntity.getTotalCuotas());
        
        List<PrestamoCuota> prestamoCuotas = executor.run(amortizacionesTask);
        
        prestamoCuotas.forEach(prestamoCuota -> {

            PrestamoCuotaAltaTask cuotaAltaTask = PrestamoCuotaAltaTask.newInstance()
                    .setPrestamoEntity(prestamoEntity)
                    .setPrestamoCuota(prestamoCuota)
                    .setPrestamoCuotaRepository(prestamoCuotaRepository);
                    
            executor.run(cuotaAltaTask);
        });
        
        PrestamoSumatoriaTask sumatoriaTask = PrestamoSumatoriaTask.newInstance()
                .setPrestamoEntity(prestamoEntity)
                .setPrestamoCuotas(prestamoCuotas);
        
        executor.run(sumatoriaTask);
        
        return prestamoEntity;
    }
}
