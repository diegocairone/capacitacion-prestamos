package com.eiv.prestamo.managers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.interfaces.Prestamo;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.services.PrestamoCuotaService;
import com.eiv.services.PrestamoService;

@Component
public class PrestamoManager {

    @Autowired private PrestamoService prestamoService;
    @Autowired private PrestamoCuotaService prestamoCuotaService;
    
    @Transactional
    public PrestamoEntity nuevo(Prestamo prestamo, UsuarioEntity usuario) {
        
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
        
        BigDecimal totalIntereses = prestamoCuotas.stream()
                .map(PrestamoCuota::getInteres)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        prestamoEntity.setTotalIntereses(totalIntereses);
        
        return prestamoEntity;
    }
}
