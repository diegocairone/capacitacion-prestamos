package com.eiv.service;

import java.util.List;

import com.eiv.interfaces.PrestamoCuota;
import com.eiv.interfaces.PrestamoDesarrolloParam;
import com.eiv.stereotype.BizService;

@BizService
public class PrestamoDesarrolloAlemanService 
        extends PrestamoDesarrolloBaseService implements PrestamoDesarrolloService {

    @Override
    public List<PrestamoCuota> calcular(PrestamoDesarrolloParam params) {
        return null;
    }
}
