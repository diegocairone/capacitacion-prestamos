package com.eiv.maths.ctf;

public class CalculadoraTasaFactory {

    public static CalculadoraTasa create(TipoTasaFinancieraEnum tipoTasaEnum) {
        switch (tipoTasaEnum) {
            case TE:
                return new CalculadoraTe();
            case TNV:
                return new CalculadoraTnv();
            case TNA:
                return new CalculadoraTna();
            default:
                throw new IllegalArgumentException(
                        "La calculadora para el tipo de tasa solicitada no existe!!");
        }
    }
}
