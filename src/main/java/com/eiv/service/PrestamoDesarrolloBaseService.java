package com.eiv.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.eiv.enums.UnidadAmortizacionEnum;
import com.eiv.interfaces.PrestamoCuota;

public abstract class PrestamoDesarrolloBaseService {
    
    protected static final int ESCALA = 6;

    protected int prdAmortDias(PrestamoDesarrolloParam params) {

        int factorPrdAmort = params.getAmortizacionUnidad()
                .equals(UnidadAmortizacionEnum.DIA) ? 1 : 30;
        int prdAmort = factorPrdAmort * params.getAmortizacionPeriodo();
        
        return prdAmort;
    }
    
    protected class PrestamoAmortizacionDto implements PrestamoCuota {

        private Integer cuota;
        private LocalDate fechaVencimiento;
        private BigDecimal capital;
        private BigDecimal interes;
        private BigDecimal saldoCapital;
        
        public Integer getCuota() {
            return cuota;
        }
        
        public void setCuota(Integer cuota) {
            this.cuota = cuota;
        }
        
        public LocalDate getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }

        public BigDecimal getCapital() {
            return capital;
        }
        
        public void setCapital(BigDecimal capital) {
            this.capital = capital;
        }
        
        public BigDecimal getInteres() {
            return interes;
        }
        
        public void setInteres(BigDecimal interes) {
            this.interes = interes;
        }

        public BigDecimal getSaldoCapital() {
            return saldoCapital;
        }

        public void setSaldoCapital(BigDecimal saldoCapital) {
            this.saldoCapital = saldoCapital;
        }

        @Override
        public String toString() {
            return "PrestamoAmortizacionDto [cuota=" + cuota 
                    + ", fechaVencimiento=" + fechaVencimiento + ", capital="
                    + capital + ", interes=" + interes + ", saldoCapital=" + saldoCapital + "]";
        }
        
    }
}
