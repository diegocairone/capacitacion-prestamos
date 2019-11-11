package com.eiv.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prestamos")
public class PrestamoEntity {

    @Id
    @Column(name = "prestamo_id", nullable = false)
    private long id;
    
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @OneToOne
    @JoinColumns({
            @JoinColumn(
                    name = "usuario_tipo_documento", referencedColumnName = "id_tipodocumento", 
                    nullable = false),
            @JoinColumn(
                    name = "usuario_numero_documento", referencedColumnName = "numero_documento", 
                    nullable = false) })
    private UsuarioEntity usuario;
    
    @OneToOne
    @JoinColumn(name = "linea_id", referencedColumnName = "linea_id", nullable = false)
    private LineaEntity linea;
    
    @Column(name = "tea", nullable = false)
    private BigDecimal tea;
    
    @Column(name = "tea_modulo", nullable = false)
    private Integer teaModulo;
    
    @Column(name = "capital_prestado", nullable = false)
    private BigDecimal capitalPrestado;
    
    @Column(name = "total_intereses", nullable = false)
    private BigDecimal totalIntereses;
    
    public PrestamoEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public LineaEntity getLinea() {
        return linea;
    }

    public void setLinea(LineaEntity linea) {
        this.linea = linea;
    }

    public BigDecimal getTea() {
        return tea;
    }

    public void setTea(BigDecimal tea) {
        this.tea = tea;
    }

    public Integer getTeaModulo() {
        return teaModulo;
    }

    public void setTeaModulo(Integer teaModulo) {
        this.teaModulo = teaModulo;
    }

    public BigDecimal getCapitalPrestado() {
        return capitalPrestado;
    }

    public void setCapitalPrestado(BigDecimal capitalPrestado) {
        this.capitalPrestado = capitalPrestado;
    }

    public BigDecimal getTotalIntereses() {
        return totalIntereses;
    }

    public void setTotalIntereses(BigDecimal totalIntereses) {
        this.totalIntereses = totalIntereses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        PrestamoEntity other = (PrestamoEntity) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PrestamoEntity [id=" + id + ", fechaAlta=" + fechaAlta + "]";
    }
    
}
