package com.eiv.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eiv.interfaces.PrestamoCuota;

@Service
public class PrestamoDesarrolloAlemanService 
        extends PrestamoDesarrolloBaseService implements PrestamoDesarrolloService {

    @Override
    public List<PrestamoCuota> calcular(PrestamoDesarrolloParam params) {
        return null;
    }
}
