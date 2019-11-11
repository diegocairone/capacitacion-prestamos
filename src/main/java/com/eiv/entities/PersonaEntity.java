package com.eiv.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eiv.enums.GeneroEnum;

@Entity
@Table(name = "personas")
public class PersonaEntity {

    @EmbeddedId
    private PersonaPkEntity pk;
    
    @OneToOne
    @JoinColumn(name = "id_tipodocumento", nullable = false, updatable = false, insertable = false)
    private TipoDocumentoEntity tipoDocumento;
    
    @Column(name = "numero_documento", nullable = false, updatable = false, insertable = false)
    private Long numeroDocumento;
    
    @Column(name = "nombre_apellido", nullable = false, length = 400)
    private String nombreApellido;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(name = "genero", nullable = false)
    private GeneroEnum genero;
    
    @Column(name = "es_argentino", nullable = false)
    private Boolean esArgentino;
    
    @Column(name = "correo_electronico", nullable = true, length = 300)
    private String email;
    
    @Lob
    @Column(name = "foto_cara", nullable = true)
    private byte[] foto;
    
    @OneToOne
    @JoinColumn(name = "id_localidad", nullable = false)
    private LocalidadEntity localidad;
    
    @Column(name = "codigo_postal", nullable = false, length = 10)
    private String codigoPostal;
    
    public PersonaEntity() {
        this.pk = new PersonaPkEntity();
    }

    public PersonaEntity(TipoDocumentoEntity tipoDocumento, Long numeroDocumento) {
        super();
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.pk = new PersonaPkEntity(tipoDocumento, numeroDocumento);
    }

    public TipoDocumentoEntity getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoEntity tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        this.pk.setTipoDocumentoId(tipoDocumento.getId());
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.pk.setNumeroDocumento(numeroDocumento);
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public GeneroEnum getGenero() {
        return genero;
    }

    public void setGenero(GeneroEnum genero) {
        this.genero = genero;
    }

    public Boolean getEsArgentino() {
        return esArgentino;
    }

    public void setEsArgentino(Boolean esArgentino) {
        this.esArgentino = esArgentino;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public LocalidadEntity getLocalidad() {
        return localidad;
    }

    public void setLocalidad(LocalidadEntity localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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
        PersonaEntity other = (PersonaEntity) obj;
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
        return "PersonaEntity [pk=" + pk + "]";
    }
    
}
