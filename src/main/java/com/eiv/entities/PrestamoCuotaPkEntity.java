package com.eiv.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrestamoCuotaPkEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "prestamo_id", nullable = false)
    private long prestamoId;
    
    @Column(name = "nro_cuota", nullable = false)
    private long cuota;
    
    public PrestamoCuotaPkEntity() {
    }

    public PrestamoCuotaPkEntity(PrestamoEntity prestamoEntity, long cuota) {
        super();
        this.prestamoId = prestamoEntity.getId();
        this.cuota = cuota;
    }

    public long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public long getCuota() {
        return cuota;
    }

    public void setCuota(long cuota) {
        this.cuota = cuota;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (cuota ^ (cuota >>> 32));
        result = prime * result + (int) (prestamoId ^ (prestamoId >>> 32));
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
        PrestamoCuotaPkEntity other = (PrestamoCuotaPkEntity) obj;
        if (cuota != other.cuota) {
            return false;
        }
        if (prestamoId != other.prestamoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PrestamoItemPkEntity [prestamoId=" + prestamoId + ", cuota=" + cuota + "]";
    }
    
}
