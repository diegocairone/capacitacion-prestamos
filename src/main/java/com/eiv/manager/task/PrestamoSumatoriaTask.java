package com.eiv.manager.task;

import java.math.BigDecimal;
import java.util.List;

import com.eiv.interfaces.PrestamoCuota;

public class PrestamoSumatoriaTask implements PrestamoTask<BigDecimal> {

    private List<PrestamoCuota> prestamoCuotas;
    
    private PrestamoSumatoriaTask() {
    }

    public static PrestamoSumatoriaTask newInstance() {
        return new PrestamoSumatoriaTask();
    }

    public PrestamoSumatoriaTask setPrestamoCuotas(List<PrestamoCuota> prestamoCuotas) {
        this.prestamoCuotas = prestamoCuotas;
        return this;
    }

    @Override
    public BigDecimal execute() {

        BigDecimal totalIntereses = prestamoCuotas.stream()
                .map(PrestamoCuota::getInteres)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return totalIntereses;
    }
}
