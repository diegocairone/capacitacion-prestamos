package com.eiv.maths.ctf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraTna implements CalculadoraTasa {

    @Override
    public BigDecimal calcular(BigDecimal razon, long modulo, long dias) {
        
        BigDecimal f1 = BigDecimal.ONE.subtract(
                BigDecimal.ONE.divide(
                        BigDecimal.ONE.add(razon), 
                        8, 
                        RoundingMode.HALF_UP));
        
        BigDecimal f2 = BigDecimal.valueOf(modulo)
                .multiply(BigDecimal.TEN)
                .multiply(BigDecimal.TEN)
                .divide(BigDecimal.valueOf(dias), 8, RoundingMode.HALF_UP);
        
        return f1.multiply(f2).setScale(2, RoundingMode.HALF_UP);
    }
}
