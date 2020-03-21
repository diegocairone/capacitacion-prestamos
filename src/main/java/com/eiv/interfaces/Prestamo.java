package com.eiv.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eiv.enums.UnidadAmortizacionEnum;

public interface Prestamo {
    
    public Long getDocumentoTipoId();
    
    public Long getNumeroDocumento();
    
    public Long getLineaId();

    public LocalDate getFechaPrimerVto();

    public BigDecimal getTasaEfectiva();

    public Integer getTasaModulo();

    public Integer getAmortizacionPeriodo();

    public UnidadAmortizacionEnum getAmortizacionUnidad();

    public BigDecimal getCapitalPrestado();
    
    public BigDecimal getTotalIntereses();

    public Integer getTotalCuotas();

}
