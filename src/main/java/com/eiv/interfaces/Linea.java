package com.eiv.interfaces;

import java.math.BigDecimal;

import com.eiv.enums.SistemaAmortizacionEnum;
import com.eiv.enums.TasaTipoEnum;
import com.eiv.enums.UnidadAmortizacionEnum;

public interface Linea {

    public String getNombre();

    public TasaTipoEnum getTasaTipo();

    public Integer getTasaModulo();

    public Integer getAmortizacionPeriodo();

    public UnidadAmortizacionEnum getAmortizacionUnidad();

    public SistemaAmortizacionEnum getSistemaAmortizacion();

    public BigDecimal getTasaMin();

    public BigDecimal getTasaMax();

    public Integer getCuotasMin();

    public Integer getCuotasMax();

    public BigDecimal getCapitalMin();

    public BigDecimal getCapitalMax();

    public Integer getEdadMax();
}
