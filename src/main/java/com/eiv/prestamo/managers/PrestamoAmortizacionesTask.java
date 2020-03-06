package com.eiv.prestamo.managers;

import java.util.List;

import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.prestamo.beans.PrestamoDesarrolloBean;
import com.eiv.prestamo.beans.PrestamoDesarrolloBeanFactory;

public class PrestamoAmortizacionesTask implements PrestamoTask<List<PrestamoCuota>> {

    private PrestamoEntity prestamo;
    
    private PrestamoAmortizacionesTask() {
    }
    
    public static PrestamoAmortizacionesTask newInstance() {
        return new PrestamoAmortizacionesTask();
    }

    public PrestamoAmortizacionesTask setPrestamo(PrestamoEntity prestamo) {
        this.prestamo = prestamo;
        return this;
    }

    @Override
    public List<PrestamoCuota> execute() {
                
        PrestamoDesarrolloBean prestamoDesarrolloBean = 
                PrestamoDesarrolloBeanFactory.create(
                        prestamo.getLinea().getSistemaAmortizacion());
        
        List<PrestamoCuota> prestamoCuotas = prestamoDesarrolloBean.calcular(prestamo);
        
        return prestamoCuotas;
    }
    
}
