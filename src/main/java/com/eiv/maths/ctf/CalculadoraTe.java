package com.eiv.maths.ctf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraTe implements CalculadoraTasa {

    @Override
    public BigDecimal calcular(BigDecimal razon, long modulo, long dias) {
        
        BigDecimal base = razon.add(BigDecimal.ONE);
        
        BigDecimal exponente = BigDecimal.valueOf(modulo)
                .divide(BigDecimal.valueOf(dias), 8, RoundingMode.HALF_UP);
        
        double potencia = Math.pow(base.doubleValue(), exponente.doubleValue());
        
        BigDecimal tasa = BigDecimal.valueOf(potencia)
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.TEN)
                .multiply(BigDecimal.TEN)
                .setScale(2, RoundingMode.HALF_UP);
        
        return tasa;
    }
}
