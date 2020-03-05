package com.eiv.maths.ctf;

public enum TipoTasaFinancieraEnum {
    TE,
    TNA,
    TNV;
    
    public static TipoTasaFinancieraEnum of(int valor) {
        switch (valor) {
            case 1:
                return TE;
            case 2:
                return TNA;
            case 3:
                return TNV;
            default:
                throw new IllegalArgumentException("No se reconoce el valor " + valor);
        }
    }
}
