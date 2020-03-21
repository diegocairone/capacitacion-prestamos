package com.eiv.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;

@Service
public class PrestamoDesarrolloAlemanService 
        extends PrestamoDesarrolloBaseService implements PrestamoDesarrolloService {

    @Override
    public List<PrestamoCuota> calcular(PrestamoEntity prestamo) {
        // TODO Auto-generated method stub
        return null;
    }

}
