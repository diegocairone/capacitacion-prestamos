package com.eiv.maths.ctf;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

public class ConversorTasaFinanciera {

    private TasaFinanciera tasaOrigen;
    private TasaFinanciera tasaDestino;
    
    public ConversorTasaFinanciera() {
        this.tasaOrigen = new TasaFinanciera();
        this.tasaDestino = new TasaFinanciera();
    }

    public ConversorTasaFinanciera(TasaFinanciera tasaOrigen) {
        this();
        this.tasaOrigen = tasaOrigen;
    }

    public TasaFinanciera getTasaOrigen() {
        return tasaOrigen;
    }

    public ConversorTasaFinanciera datosIniciales(Consumer<TasaFinanciera> consumer) {
        consumer.accept(tasaOrigen);
        return this;
    }
    
    public ConversorTasaFinanciera convertir(
            TipoTasaFinancieraEnum tipoTasaDestino, Long moduloDestino, Long dias) {
        
        // PASO 1 - CALCULAR LA RAZON
        BigDecimal razon = CalculadoraRazonFactory
                .create(tasaOrigen.getTipo())
                .calcular(tasaOrigen, dias);

        // PASO 1 - CALCULAR LA TASA DESTINO
        BigDecimal tasa = CalculadoraTasaFactory
                .create(tipoTasaDestino)
                .calcular(razon, moduloDestino, dias);
        
        this.tasaDestino.setValor(tasa);
        this.tasaDestino.setModulo(moduloDestino);
        this.tasaDestino.setTipo(tipoTasaDestino);
        
        return this;
    }
    
    public Optional<TasaFinanciera> resultado() {
        return tasaDestino == null ? Optional.empty() : Optional.of(tasaDestino);
    }
}
