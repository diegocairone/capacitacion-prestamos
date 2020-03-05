package com.eiv.maths.ctf;

import java.math.BigDecimal;

public interface CalculadoraRazon {

    public BigDecimal calcular(TasaFinanciera tasa, long dias);
}
