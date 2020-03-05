package com.eiv.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eiv.enums.UnidadAmortizacionEnum;

public interface Prestamo {
    
    public Long getDocumentoTipoId();
    
    public Long getNumeroDocumento();
    
    public Long getLineaId();

    public LocalDate getFechaAlta();

    public BigDecimal getTasaEfectiva();

    public Integer getTasaModulo();

    public Integer getAmortizacionesCantidad();

    public UnidadAmortizacionEnum getAmortizacionesUnidad();

    public BigDecimal getCapitalPrestado();

    public Integer getTotalCuotas();

}
