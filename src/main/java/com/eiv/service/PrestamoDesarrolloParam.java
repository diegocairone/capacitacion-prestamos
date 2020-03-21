package com.eiv.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eiv.enums.SistemaAmortizacionEnum;
import com.eiv.enums.UnidadAmortizacionEnum;

public interface PrestamoDesarrolloParam {

    public SistemaAmortizacionEnum getAmortizacionSistema();
    
    public UnidadAmortizacionEnum getAmortizacionUnidad();
    
    public Integer getAmortizacionPeriodo();
    
    public BigDecimal getTasaEfectiva();
    
    public Integer getTasaModulo();
    
    public BigDecimal getCapitalPrestado();
    
    public Integer getTotalCuotas();
    
    public LocalDate getFechaPrimerVto();
    
}
