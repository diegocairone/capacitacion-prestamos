package com.eiv.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.eiv.interfaces.PrestamoCuota;
import com.eiv.interfaces.PrestamoDesarrolloParam;
import com.eiv.maths.ctf.ConversorTasaFinanciera;
import com.eiv.maths.ctf.TipoTasaFinancieraEnum;
import com.eiv.stereotype.BizService;

@BizService
public class PrestamoDesarrolloFrancesService 
        extends PrestamoDesarrolloBaseService implements PrestamoDesarrolloService {
    
    @Override
    public List<PrestamoCuota> calcular(PrestamoDesarrolloParam params) {
        
        final long prdAmort = prdAmortDias(params);
        final List<PrestamoCuota> prestamoCuotas = new ArrayList<PrestamoCuota>();
 
        ConversorTasaFinanciera conversor = new ConversorTasaFinanciera();
        
        Optional<BigDecimal> optional = conversor
                .datosIniciales(tf -> {
                    tf.setModulo((long)params.getTasaModulo());
                    tf.setTipo(TipoTasaFinancieraEnum.TE);
                    tf.setValor(params.getTasaEfectiva());
                })
                .convertir(TipoTasaFinancieraEnum.TNV, (long)params.getTasaModulo(), prdAmort)
                .getRazon();
        
        final BigDecimal razon = optional.get();
        final BigDecimal vc = calculoCuota(
                params.getCapitalPrestado(), razon, params.getTotalCuotas());
        
        // DESARROLLO CUOTAS - PRIMER CUOTA

        LocalDate fechaVencimiento = params.getFechaPrimerVto();
        BigDecimal saldoCapital = params.getCapitalPrestado();
        BigDecimal interes = saldoCapital.multiply(razon).setScale(2, RoundingMode.HALF_UP);
        BigDecimal capital = vc.subtract(interes);
        BigDecimal nuevoSaldoCapital = saldoCapital.subtract(capital);
        
        PrestamoAmortizacionDto prestamoPrimeraCuota = new PrestamoAmortizacionDto();
        
        prestamoPrimeraCuota.setCuota(1);
        prestamoPrimeraCuota.setFechaVencimiento(fechaVencimiento);
        prestamoPrimeraCuota.setCapital(capital);
        prestamoPrimeraCuota.setInteres(interes);
        prestamoPrimeraCuota.setSaldoCapital(nuevoSaldoCapital);
        
        prestamoCuotas.add(prestamoPrimeraCuota);
        
        for (int i = 1; i < params.getTotalCuotas(); i++) {

            fechaVencimiento = fechaVencimiento.plusDays(prdAmort);
            saldoCapital = nuevoSaldoCapital;
            interes = saldoCapital.multiply(razon).setScale(2, RoundingMode.HALF_UP);
            capital = vc.subtract(interes);
            nuevoSaldoCapital = saldoCapital.subtract(capital);

            PrestamoAmortizacionDto prestamoCuota = new PrestamoAmortizacionDto();
            
            prestamoCuota.setCuota(i + 1);
            prestamoCuota.setFechaVencimiento(fechaVencimiento);
            prestamoCuota.setCapital(capital);
            prestamoCuota.setInteres(interes);
            prestamoCuota.setSaldoCapital(nuevoSaldoCapital);
            
            prestamoCuotas.add(prestamoCuota);
        }
        
        return prestamoCuotas;
    }

    protected BigDecimal calculoCuota(BigDecimal capital, BigDecimal razon, Integer cuotas) {

        BigDecimal factor = razon.add(BigDecimal.ONE).pow(cuotas)
                .setScale(ESCALA, RoundingMode.HALF_UP);

        BigDecimal resultado = capital.multiply(razon).multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), ESCALA, RoundingMode.HALF_UP);

        return resultado.setScale(2, RoundingMode.HALF_UP);
    }    
}
