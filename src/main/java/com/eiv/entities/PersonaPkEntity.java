package com.eiv.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PersonaPkEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_tipodocumento", nullable = false)
    private long tipoDocumentoId;
    
    @Column(name = "numero_documento", nullable = false)
    private long numeroDocumento;
    
    public PersonaPkEntity() {
    }

    public PersonaPkEntity(long tipoDocumentoId, long numeroDocumento) {
        super();
        this.tipoDocumentoId = tipoDocumentoId;
        this.numeroDocumento = numeroDocumento;
    }

    public PersonaPkEntity(TipoDocumentoEntity tipoDocumentoEntity, long numeroDocumento) {
        super();
        this.tipoDocumentoId = tipoDocumentoEntity == null ? null : tipoDocumentoEntity.getId();
        this.numeroDocumento = numeroDocumento;
    }

    public long getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(long tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (numeroDocumento ^ (numeroDocumento >>> 32));
        result = prime * result + (int) (tipoDocumentoId ^ (tipoDocumentoId >>> 32));
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
        PersonaPkEntity other = (PersonaPkEntity) obj;
        if (numeroDocumento != other.numeroDocumento) {
            return false;
        }
        if (tipoDocumentoId != other.tipoDocumentoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonaPkEntity [tipoDocumentoId=" + tipoDocumentoId 
                + ", numeroDocumento=" + numeroDocumento + "]";
    }
}
