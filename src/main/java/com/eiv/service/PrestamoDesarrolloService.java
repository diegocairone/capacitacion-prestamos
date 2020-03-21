package com.eiv.service;

import java.util.List;

import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;

public interface PrestamoDesarrolloService {

    public List<PrestamoCuota> calcular(PrestamoEntity prestamo);
}
