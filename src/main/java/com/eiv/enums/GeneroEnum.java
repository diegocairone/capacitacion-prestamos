package com.eiv.enums;

public enum GeneroEnum {
    MASCULINO('M'),
    FEMENINO('F');
    
    private char value;
    
    private GeneroEnum(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
    
    public static GeneroEnum of(char value) {

        if (value == 'M') {
            return MASCULINO;
        } else if (value == 'F') {
            return FEMENINO;
        } else {
            throw new IllegalArgumentException(
                    String.format("No se reconoce %s como un genero!", value));
        }
    }
}
