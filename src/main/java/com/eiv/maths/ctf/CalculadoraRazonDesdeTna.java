package com.eiv.maths.ctf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraRazonDesdeTna implements CalculadoraRazon {

    @Override
    public BigDecimal calcular(TasaFinanciera tasa, long dias) {
        
        BigDecimal factor = tasa.getValor().multiply(BigDecimal.valueOf(dias))
                .divide(BigDecimal.TEN.multiply(BigDecimal.TEN))
                .divide(BigDecimal.valueOf(tasa.getModulo()), 8, RoundingMode.HALF_UP);
        
        return BigDecimal.ONE.divide(
                BigDecimal.ONE.subtract(factor), 
                8, 
                RoundingMode.HALF_UP)
                .subtract(BigDecimal.ONE);
    }
}
