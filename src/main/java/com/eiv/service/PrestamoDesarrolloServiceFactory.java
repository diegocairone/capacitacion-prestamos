package com.eiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eiv.enums.SistemaAmortizacionEnum;

@Service
public class PrestamoDesarrolloServiceFactory {

    private static PrestamoDesarrolloAlemanService ALEMAN_BEAN;
    private static PrestamoDesarrolloFrancesService FRANCES_BEAN;
    
    @Autowired
    public PrestamoDesarrolloServiceFactory(
            PrestamoDesarrolloAlemanService prestamoDesarrolloAlemanBean,
            PrestamoDesarrolloFrancesService prestamoDesarrolloFrancesBean) {
        
        PrestamoDesarrolloServiceFactory.ALEMAN_BEAN = prestamoDesarrolloAlemanBean;
        PrestamoDesarrolloServiceFactory.FRANCES_BEAN = prestamoDesarrolloFrancesBean;
    }
    
    public static PrestamoDesarrolloService create(SistemaAmortizacionEnum sistemaAmortizacion) {
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
