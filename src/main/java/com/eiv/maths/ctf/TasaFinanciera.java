package com.eiv.maths.ctf;

import java.math.BigDecimal;

public class TasaFinanciera {

    private BigDecimal valor;
    private TipoTasaFinancieraEnum tipo;
    private Long modulo;
    
    public TasaFinanciera() {
    }

    public TasaFinanciera(BigDecimal valor, TipoTasaFinancieraEnum tipo, Long modulo) {
        super();
        this.valor = valor;
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTasaFinancieraEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoTasaFinancieraEnum tipo) {
        this.tipo = tipo;
    }

    public Long getModulo() {
        return modulo;
    }

    public void setModulo(Long modulo) {
        this.modulo = modulo;
    }

    @Override
    public String toString() {
        return "Tasa [valor=" + valor + ", tipo=" + tipo + ", modulo=" + modulo + "]";
    }
}
