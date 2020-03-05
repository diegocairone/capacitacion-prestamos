package com.eiv.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PrestamoCuota {

    public Integer getCuota();
    
    public LocalDate getFechaVencimiento();

    public BigDecimal getCapital();

    public BigDecimal getInteres();

}
