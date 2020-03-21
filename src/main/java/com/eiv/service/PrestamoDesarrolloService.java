package com.eiv.service;

import java.util.List;

import com.eiv.interfaces.PrestamoCuota;

public interface PrestamoDesarrolloService {

    public List<PrestamoCuota> calcular(PrestamoDesarrolloParam params);
}
