package com.eiv.enums;

public enum UnidadAmortizacionEnum {
    DIA('D'),
    MES('M');

    private char value;
    
    private UnidadAmortizacionEnum(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static UnidadAmortizacionEnum of(char value) {
        if (value == 'D') {
            return DIA;
        } else if (value == 'M') {
            return MES;
        } else {
            throw new IllegalArgumentException(String.format(
                    "No se reconoce el tipo de Unidad de Amortizacion %s", value));
        }
    }
}
