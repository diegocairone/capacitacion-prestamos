package com.eiv.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eiv.enums.UnidadAmortizacionEnum;

public interface PrestamoSolicitudFrm {
    
    public Long getDocumentoTipoId();
    
    public Long getNumeroDocumento();
    
    public Long getLineaId();

    public LocalDate getFechaPrimerVto();

    public BigDecimal getTasaEfectiva();

    public Integer getTasaModulo();

    public Integer getAmortizacionPeriodo();

    public UnidadAmortizacionEnum getAmortizacionUnidad();

    public BigDecimal getCapitalPrestado();

    public Integer getTotalCuotas();

}
