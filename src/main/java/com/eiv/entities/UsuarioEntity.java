package com.eiv.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @EmbeddedId
    private PersonaPkEntity pk;
    
    @OneToOne
    @MapsId("personaPkEntity")
    @JoinColumns({ 
            @JoinColumn(name = "id_tipodocumento", referencedColumnName = "id_tipodocumento"),
            @JoinColumn(name = "numero_documento", referencedColumnName = "numero_documento") })
    private PersonaEntity persona;
    
    @Column(name = "nombre_usuario", nullable = false, length = 50, unique = true)
    private String nombreUsuario;
    
    @Column(name = "hashed_pwd", nullable = false, length = 200)
    private String hashedpwd;
    
    public UsuarioEntity() {
        this.pk = new PersonaPkEntity();
    }

    public UsuarioEntity(PersonaEntity persona) {
        super();
        this.persona = persona;
        this.pk = new PersonaPkEntity(persona.getTipoDocumento(), persona.getNumeroDocumento());
    }

    public PersonaEntity getPersona() {
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
        this.pk.setTipoDocumentoId(persona.getTipoDocumento().getId());
        this.pk.setNumeroDocumento(persona.getNumeroDocumento());
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getHashedpwd() {
        return hashedpwd;
    }

    public void setHashedpwd(String hashedpwd) {
        this.hashedpwd = hashedpwd;
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
        UsuarioEntity other = (UsuarioEntity) obj;
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
        return "UsuarioEntity [persona=" + persona + ", nombreUsuario=" + nombreUsuario + "]";
    }
    
}
