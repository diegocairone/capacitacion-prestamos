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

import com.eiv.enums.SistemaAmortizacionEnum;
import com.eiv.enums.TasaTipoEnum;
import com.eiv.enums.UnidadAmortizacionEnum;

@Entity
@Table(name = "lineas")
public class LineaEntity {

    @Id
    @Column(name = "linea_id", nullable = false)
    private long id;
    
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    
    @Column(name = "sistema_amortizacion", nullable = false, length = 1)
    private SistemaAmortizacionEnum sistemaAmortizacion;

    @Column(name = "tasa_tipo", nullable = false, length = 1)
    private TasaTipoEnum tasaTipo;
    
    @Column(name = "tasa_modulo", nullable = false)
    private Integer tasaModulo;

    @Column(name = "amortizacion_periodo", nullable = false)
    private Integer amortizacionPeriodo;

    @Column(name = "amortizacion_unidad", nullable = false)
    private UnidadAmortizacionEnum amortizacionUnidad;
    
    @Column(name = "tasa_min", nullable = false)
    private BigDecimal tasaMin;

    @Column(name = "tasa_max", nullable = false)
    private BigDecimal tasaMax;

    @Column(name = "cuotas_min", nullable = false)
    private Integer cuotasMin;
    
    @Column(name = "cuotas_max", nullable = false)
    private Integer cuotasMax;
    
    @Column(name = "capital_min", nullable = false)
    private BigDecimal capitalMin;
    
    @Column(name = "capital_max", nullable = false)
    private BigDecimal capitalMax;
    
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
    
    public LineaEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TasaTipoEnum getTasaTipo() {
        return tasaTipo;
    }

    public void setTasaTipo(TasaTipoEnum tasaTipo) {
        this.tasaTipo = tasaTipo;
    }

    public Integer getTasaModulo() {
        return tasaModulo;
    }

    public void setTasaModulo(Integer tasaModulo) {
        this.tasaModulo = tasaModulo;
    }

    public Integer getAmortizacionPeriodo() {
        return amortizacionPeriodo;
    }

    public void setAmortizacionPeriodo(Integer amortizacionPeriodo) {
        this.amortizacionPeriodo = amortizacionPeriodo;
    }

    public UnidadAmortizacionEnum getAmortizacionUnidad() {
        return amortizacionUnidad;
    }

    public void setAmortizacionUnidad(UnidadAmortizacionEnum amortizacionUnidad) {
        this.amortizacionUnidad = amortizacionUnidad;
    }

    public SistemaAmortizacionEnum getSistemaAmortizacion() {
        return sistemaAmortizacion;
    }

    public void setSistemaAmortizacion(SistemaAmortizacionEnum sistemaAmortizacion) {
        this.sistemaAmortizacion = sistemaAmortizacion;
    }

    public BigDecimal getTasaMin() {
        return tasaMin;
    }

    public void setTasaMin(BigDecimal tasaMin) {
        this.tasaMin = tasaMin;
    }

    public BigDecimal getTasaMax() {
        return tasaMax;
    }

    public void setTasaMax(BigDecimal tasaMax) {
        this.tasaMax = tasaMax;
    }

    public Integer getCuotasMin() {
        return cuotasMin;
    }

    public void setCuotasMin(Integer cuotasMin) {
        this.cuotasMin = cuotasMin;
    }

    public Integer getCuotasMax() {
        return cuotasMax;
    }

    public void setCuotasMax(Integer cuotasMax) {
        this.cuotasMax = cuotasMax;
    }

    public BigDecimal getCapitalMin() {
        return capitalMin;
    }

    public void setCapitalMin(BigDecimal capitalMin) {
        this.capitalMin = capitalMin;
    }

    public BigDecimal getCapitalMax() {
        return capitalMax;
    }

    public void setCapitalMax(BigDecimal capitalMax) {
        this.capitalMax = capitalMax;
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
        LineaEntity other = (LineaEntity) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LineaEntity [id=" + id + ", nombre=" + nombre + "]";
    }
    
}
