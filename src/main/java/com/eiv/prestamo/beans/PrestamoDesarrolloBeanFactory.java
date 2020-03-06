package com.eiv.prestamo.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eiv.enums.SistemaAmortizacionEnum;

@Service
public class PrestamoDesarrolloBeanFactory {

    private static PrestamoDesarrolloAlemanBean ALEMAN_BEAN;
    private static PrestamoDesarrolloFrancesBean FRANCES_BEAN;
    
    @Autowired
    public PrestamoDesarrolloBeanFactory(
            PrestamoDesarrolloAlemanBean prestamoDesarrolloAlemanBean,
            PrestamoDesarrolloFrancesBean prestamoDesarrolloFrancesBean) {
        
        PrestamoDesarrolloBeanFactory.ALEMAN_BEAN = prestamoDesarrolloAlemanBean;
        PrestamoDesarrolloBeanFactory.FRANCES_BEAN = prestamoDesarrolloFrancesBean;
    }
    
    public static PrestamoDesarrolloBean create(SistemaAmortizacionEnum sistemaAmortizacion) {
        switch (sistemaAmortizacion) {
            case SISTEMA_ALEMAN:
                return ALEMAN_BEAN;
            case SISTEMA_FRANCES:
                return FRANCES_BEAN;
            default:
                throw new IllegalArgumentException(
                        String.format("EL SISTEMA NO ESTA IMPLMENTADO: %s", sistemaAmortizacion));
        }
    }
}
