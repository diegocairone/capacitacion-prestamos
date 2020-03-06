package com.eiv.prestamo.beans;

import java.util.List;

import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;

public interface PrestamoDesarrolloBean {

    public List<PrestamoCuota> calcular(PrestamoEntity prestamo);
}
