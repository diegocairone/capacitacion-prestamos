package com.eiv.maths.ctf;

import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ConversorTnvParaTeTest {

    public static ConversorTasaFinanciera CONVERSOR;
    
    static {
        CONVERSOR = new ConversorTasaFinanciera();
    }
    
    @Parameter(0) public long moduloOrigen;
    @Parameter(1) public long moduloDestino;
    @Parameter(2) public BigDecimal tasaOrigen;
    @Parameter(3) public BigDecimal tasaEsperada;
    @Parameter(4) public long dias;

    @Parameters(name = "{index}: MODULO: {0}, TIPO: {1}, VALOR: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { 
            { 360, 360, BigDecimal.valueOf(47.93), BigDecimal.valueOf(60), 30 }
        });
    }
    
    @Test
    public void givenTnv_thenTe() {
        
        CONVERSOR
                .datosIniciales(tasa -> {
                    tasa.setModulo(moduloOrigen);
                    tasa.setTipo(TipoTasaFinancieraEnum.TNV);
                    tasa.setValor(tasaOrigen);
                })
                .convertir(TipoTasaFinancieraEnum.TE, moduloDestino, dias)
                .getResultado()
                .ifPresent(tasa -> {
                    assertThat(tasa.getValor().compareTo(tasaEsperada), Is.is(0));         
                });
    }
}
