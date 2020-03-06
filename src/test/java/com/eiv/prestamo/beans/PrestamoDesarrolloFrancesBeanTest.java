package com.eiv.prestamo.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eiv.entities.PrestamoEntity;
import com.eiv.enums.UnidadAmortizacionEnum;
import com.eiv.interfaces.PrestamoCuota;

public class PrestamoDesarrolloFrancesBeanTest {

    @Test
    public void givenPrestamoEntity_thenTblAmort12Cuotas() {
        
        PrestamoEntity prestamoEntity = new PrestamoEntity();
        
        prestamoEntity.setId(0L);
        prestamoEntity.setPersona(null);
        prestamoEntity.setLinea(null);
        prestamoEntity.setFechaAlta(LocalDate.now());
        prestamoEntity.setFechaPrimerVto(LocalDate.now().plusDays(30));
        prestamoEntity.setTasaEfectiva(BigDecimal.valueOf(35));
        prestamoEntity.setTasaModulo(365);
        prestamoEntity.setAmortizacionPeriodo(30);
        prestamoEntity.setAmortizacionUnidad(UnidadAmortizacionEnum.DIA);
        prestamoEntity.setCapitalPrestado(BigDecimal.valueOf(100000));
        prestamoEntity.setTotalCuotas(12);
        
        PrestamoDesarrolloFrancesBean bean = new PrestamoDesarrolloFrancesBean();
        List<PrestamoCuota> prestamoCuotas = bean.calcular(prestamoEntity);
        
        Assertions.assertThat(prestamoCuotas).hasSize(12);
        
        prestamoCuotas.forEach(e -> System.out.println(e));
    }
}
