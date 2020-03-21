package com.eiv.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eiv.enums.SistemaAmortizacionEnum;
import com.eiv.enums.UnidadAmortizacionEnum;
import com.eiv.interfaces.PrestamoCuota;

public class PrestamoDesarrolloFrancesServiceTest {

    @Test
    public void givenPrestamoEntity_thenTblAmort12Cuotas() {
        
        PrestamoDesarrolloParam prestamo = new PrestamoDesarrolloParam() {
            
            @Override
            public Integer getTotalCuotas() {
                return 12;
            }
            
            @Override
            public Integer getTasaModulo() {
                return 365;
            }
            
            @Override
            public BigDecimal getTasaEfectiva() {
                return BigDecimal.valueOf(35);
            }
            
            @Override
            public LocalDate getFechaPrimerVto() {
                return LocalDate.now().plusDays(30);
            }
            
            @Override
            public BigDecimal getCapitalPrestado() {
                return BigDecimal.valueOf(100000);
            }
            
            @Override
            public UnidadAmortizacionEnum getAmortizacionUnidad() {
                return UnidadAmortizacionEnum.DIA;
            }
            
            @Override
            public Integer getAmortizacionPeriodo() {
                return 30;
            }

            @Override
            public SistemaAmortizacionEnum getAmortizacionSistema() {
                return SistemaAmortizacionEnum.SISTEMA_FRANCES;
            }
        };
        
        PrestamoDesarrolloFrancesService bean = new PrestamoDesarrolloFrancesService();
        List<PrestamoCuota> prestamoCuotas = bean.calcular(prestamo);
        
        Assertions.assertThat(prestamoCuotas).hasSize(12);
        
        prestamoCuotas.forEach(e -> System.out.println(e));
    }
}
