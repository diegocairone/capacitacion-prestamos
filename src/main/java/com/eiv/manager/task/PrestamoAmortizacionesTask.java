package com.eiv.manager.task;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.eiv.enums.SistemaAmortizacionEnum;
import com.eiv.enums.UnidadAmortizacionEnum;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.interfaces.PrestamoDesarrolloParam;
import com.eiv.service.PrestamoDesarrolloService;
import com.eiv.service.PrestamoDesarrolloServiceFactory;

public class PrestamoAmortizacionesTask implements PrestamoTask<List<PrestamoCuota>> {

    private Integer totalCuotas;
    private Integer tasaModulo;
    private BigDecimal tasaEfectiva;
    private LocalDate fechaPrimerVto;
    private BigDecimal capitalPrestado;
    private Integer amortizacionPeriodo;
    private UnidadAmortizacionEnum amortizacionUnidad;
    private SistemaAmortizacionEnum amortizacionSistema;
    
    private PrestamoAmortizacionesTask() {
    }
    
    public static PrestamoAmortizacionesTask newInstance() {
        return new PrestamoAmortizacionesTask();
    }

    public PrestamoAmortizacionesTask setTotalCuotas(Integer totalCuotas) {
        this.totalCuotas = totalCuotas;
        return this;
    }

    public PrestamoAmortizacionesTask setTasaModulo(Integer tasaModulo) {
        this.tasaModulo = tasaModulo;
        return this;
    }

    public PrestamoAmortizacionesTask setTasaEfectiva(BigDecimal tasaEfectiva) {
        this.tasaEfectiva = tasaEfectiva;
        return this;
    }

    public PrestamoAmortizacionesTask setFechaPrimerVto(LocalDate fechaPrimerVto) {
        this.fechaPrimerVto = fechaPrimerVto;
        return this;
    }

    public PrestamoAmortizacionesTask setCapitalPrestado(BigDecimal capitalPrestado) {
        this.capitalPrestado = capitalPrestado;
        return this;
    }

    public PrestamoAmortizacionesTask setAmortizacionPeriodo(Integer amortizacionPeriodo) {
        this.amortizacionPeriodo = amortizacionPeriodo;
        return this;
    }

    public PrestamoAmortizacionesTask setAmortizacionUnidad(
            UnidadAmortizacionEnum amortizacionUnidad) {
        this.amortizacionUnidad = amortizacionUnidad;
        return this;
    }

    public PrestamoAmortizacionesTask setAmortizacionSistema(
            SistemaAmortizacionEnum amortizacionSistema) {
        this.amortizacionSistema = amortizacionSistema;
        return this;
    }

    @Override
    public List<PrestamoCuota> execute() {
                
        PrestamoDesarrolloService prestamoDesarrolloBean = 
                PrestamoDesarrolloServiceFactory.create(amortizacionSistema);
        
        List<PrestamoCuota> prestamoCuotas = 
                prestamoDesarrolloBean.calcular(new PrestamoDesarrolloParam() {
                    
                    @Override
                    public Integer getTotalCuotas() {
                        return totalCuotas;
                    }
                    
                    @Override
                    public Integer getTasaModulo() {
                        return tasaModulo;
                    }
                    
                    @Override
                    public BigDecimal getTasaEfectiva() {
                        return tasaEfectiva;
                    }
                    
                    @Override
                    public LocalDate getFechaPrimerVto() {
                        return fechaPrimerVto;
                    }
                    
                    @Override
                    public BigDecimal getCapitalPrestado() {
                        return capitalPrestado;
                    }
                    
                    @Override
                    public UnidadAmortizacionEnum getAmortizacionUnidad() {
                        return amortizacionUnidad;
                    }
                    
                    @Override
                    public Integer getAmortizacionPeriodo() {
                        return amortizacionPeriodo;
                    }

                    @Override
                    public SistemaAmortizacionEnum getAmortizacionSistema() {
                        return amortizacionSistema;
                    }
                });
        
        return prestamoCuotas;
    }    
}
