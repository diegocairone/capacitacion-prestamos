package com.eiv.enums;

public enum TasaTipoEnum {
    EFECTIVA('E'),
    NOMINAL('N');
    
    private char value;
    
    private TasaTipoEnum(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static TasaTipoEnum of(char value) {
        if (value == 'E') {
            return EFECTIVA;
        } else if (value == 'N') {
            return NOMINAL;
        } else {
            throw new IllegalArgumentException(String.format(
                    "No se reconoce el tipo de tasa %s", value));
        }
    }
}
