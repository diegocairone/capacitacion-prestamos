package com.eiv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "tipos_documentos")
public class TipoDocumentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "generador")
    @TableGenerator(
            table = "SEQUENCE_TABLE", name = "generador", 
            pkColumnName = "seq_name", valueColumnName = "seq_value", 
            pkColumnValue = "tipo_documento_seq",
            allocationSize = 1, initialValue = 1)
    @Column(name = "id_tipodocumento", nullable = false)
    private Long id;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 200)
    private String nombre;

    @Column(name = "abreviatura", nullable = false, length = 5)
    private String abreviatura;
    
    @Column(name = "validar_como_cuit", nullable = false)
    private Boolean validarComoCuit;
    
    public TipoDocumentoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Boolean getValidarComoCuit() {
        return validarComoCuit;
    }

    public void setValidarComoCuit(Boolean validarComoCuit) {
        this.validarComoCuit = validarComoCuit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        TipoDocumentoEntity other = (TipoDocumentoEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoDocumentoEntity [id=" + id + ", nombre=" + nombre + "]";
    }
    
}
