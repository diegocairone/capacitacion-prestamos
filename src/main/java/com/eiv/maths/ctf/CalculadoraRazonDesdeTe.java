package com.eiv.maths.ctf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraRazonDesdeTe implements CalculadoraRazon {

    @Override
    public BigDecimal calcular(TasaFinanciera tasa, long dias) {
        
        BigDecimal base = tasa.getValor()
                .divide(BigDecimal.TEN.multiply(BigDecimal.TEN))
                .add(BigDecimal.ONE);
        
        BigDecimal exponente = BigDecimal.valueOf(dias)
                .divide(BigDecimal.valueOf(tasa.getModulo()), 8, RoundingMode.HALF_UP);
        
        double potencia = Math.pow(base.doubleValue(), exponente.doubleValue());
        BigDecimal razon = BigDecimal.valueOf(potencia)
                .subtract(BigDecimal.ONE)
                .setScale(8, RoundingMode.HALF_UP);
        
        return razon;
    }
}
