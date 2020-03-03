package com.eiv.interfaces;

import java.time.LocalDate;

import com.eiv.enums.GeneroEnum;

public interface Persona {

    public Long getTipoDocumentoId();

    public String getNumeroDocumento();

    public String getNombreApellido();

    public LocalDate getFechaNacimiento();

    public GeneroEnum getGenero();

    public Boolean getEsArgentino();

    public String getEmail();

    public byte[] getFoto();

    public Long getLocalidadId();

    public String getCodigoPostal();

}
