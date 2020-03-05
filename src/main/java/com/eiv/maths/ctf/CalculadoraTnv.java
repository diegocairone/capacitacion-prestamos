package com.eiv.maths.ctf;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculadoraTnv implements CalculadoraTasa {

    @Override
    public BigDecimal calcular(BigDecimal razon, long modulo, long dias) {
        return razon
                .multiply(BigDecimal.valueOf(modulo))
                .multiply(BigDecimal.TEN)
                .multiply(BigDecimal.TEN)
                .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
    }
}
