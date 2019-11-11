package com.eiv.enums;

public enum SistemaAmortizacionEnum {
    SISTEMA_FRANCES('F'),
    SISTEMA_ALEMAN('A');
    
    private char value;
    
    private SistemaAmortizacionEnum(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
    
    public static SistemaAmortizacionEnum of(char value) {
        if (value == 'F') {
            return SISTEMA_FRANCES;
        } else if (value == 'A') {
            return SISTEMA_ALEMAN;
        } else {
            throw new IllegalArgumentException(String.format(
                    "No se reconoce el tipo de sistema de amortizacion %s", value));
        }
    }
}
