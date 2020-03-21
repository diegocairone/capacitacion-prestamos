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

    @Autowired private PrestamoRepository prestamoService;
    @Autowired private PrestamoCuotaRepository prestamoCuotaService;
    
    @Transactional
    public PrestamoEntity solicitar(Prestamo prestamo, UsuarioEntity usuario) {
        
        final PrestamoTaskExecutor executor = new PrestamoTaskExecutor();
        
        PrestamoAltaTask altaTask = PrestamoAltaTask.newInstance()
                .setPrestamo(prestamo)
                .setUsuario(usuario)
                .setPrestamoService(prestamoService);
        
        PrestamoEntity prestamoEntity = executor.run(altaTask);
        
        PrestamoAmortizacionesTask amortizacionesTask = PrestamoAmortizacionesTask
                .newInstance()
                .setPrestamo(prestamoEntity);
        
        List<PrestamoCuota> prestamoCuotas = executor.run(amortizacionesTask);
        
        prestamoCuotas.forEach(prestamoCuota -> {

            PrestamoCuotaAltaTask cuotaAltaTask = PrestamoCuotaAltaTask.newInstance()
                    .setPrestamoEntity(prestamoEntity)
                    .setPrestamoCuota(prestamoCuota)
                    .setPrestamoCuotaService(prestamoCuotaService);
                    
            executor.run(cuotaAltaTask);
        });
        
        PrestamoSumatoriaTask sumatoriaTask = PrestamoSumatoriaTask.newInstance()
                .setPrestamoEntity(prestamoEntity)
                .setPrestamoCuotas(prestamoCuotas);
        
        executor.run(sumatoriaTask);
        
        return prestamoEntity;
    }
}
