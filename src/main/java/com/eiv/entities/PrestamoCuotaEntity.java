package com.eiv.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;

@Entity
@Table(name = "prestamos_cuotas")
public class PrestamoCuotaEntity {

    @EmbeddedId
    @QueryType(PropertyType.NONE)
    private PrestamoCuotaPkEntity pk;
    
    @OneToOne
    @JoinColumn(name = "prestamo_id", referencedColumnName = "prestamo_id", 
            nullable = false, insertable = false, updatable = false)
    private PrestamoEntity prestamo;
    
    @Column(name = "cuota", nullable = false, insertable = false, updatable = false)
    private Integer cuota;
    
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    
    @Column(name = "importe_capital", nullable = false)
    private BigDecimal capital;
    
    @Column(name = "importe_interes", nullable = false)
    private BigDecimal interes;
    
    @Column(name = "importe_total", nullable = false)
    private BigDecimal total;

    @Column(name = "saldo_capital", nullable = false)
    private BigDecimal saldoCapital;
    
    public PrestamoCuotaEntity() {
        this.pk = new PrestamoCuotaPkEntity();
    }

    public PrestamoCuotaEntity(PrestamoEntity prestamo, Integer cuota) {
        super();
        this.prestamo = prestamo;
        this.cuota = cuota;
        this.pk = new PrestamoCuotaPkEntity(prestamo, cuota);
    }

    public PrestamoEntity getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PrestamoEntity prestamo) {
        this.prestamo = prestamo;
    }

    public Integer getCuota() {
        return cuota;
    }

    public void setCuota(Integer cuota) {
        this.cuota = cuota;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSaldoCapital() {
        return saldoCapital;
    }

    public void setSaldoCapital(BigDecimal saldoCapital) {
        this.saldoCapital = saldoCapital;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PrestamoCuotaEntity other = (PrestamoCuotaEntity) obj;
        if (pk == null) {
            if (other.pk != null) {
                return false;
            }
        } else if (!pk.equals(other.pk)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PrestamoCuotaEntity [prestamo=" + prestamo + ", cuota=" + cuota + "]";
    }
    
}
