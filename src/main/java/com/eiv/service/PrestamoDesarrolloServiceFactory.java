package com.eiv.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.eiv.enums.SistemaAmortizacionEnum;
import com.eiv.stereotype.BizService;

@BizService
public class PrestamoDesarrolloServiceFactory {

    private static PrestamoDesarrolloAlemanService ALEMAN_SERVICE;
    private static PrestamoDesarrolloFrancesService FRANCES_SERVICE;
    
    @Autowired
    public PrestamoDesarrolloServiceFactory(
            PrestamoDesarrolloAlemanService prestamoDesarrolloAlemanService,
            PrestamoDesarrolloFrancesService prestamoDesarrolloFrancesService) {
        
        PrestamoDesarrolloServiceFactory.ALEMAN_SERVICE = prestamoDesarrolloAlemanService;
        PrestamoDesarrolloServiceFactory.FRANCES_SERVICE = prestamoDesarrolloFrancesService;
    }
    
    public static PrestamoDesarrolloService create(SistemaAmortizacionEnum sistemaAmortizacion) {
        switch (sistemaAmortizacion) {
            case SISTEMA_ALEMAN:
                return ALEMAN_SERVICE;
            case SISTEMA_FRANCES:
                return FRANCES_SERVICE;
            default:
                throw new IllegalArgumentException(
                        String.format("EL SISTEMA NO ESTA IMPLMENTADO: %s", sistemaAmortizacion));
        }
    }
}
