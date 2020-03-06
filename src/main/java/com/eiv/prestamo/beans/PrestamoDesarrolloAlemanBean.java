package com.eiv.prestamo.beans;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;

@Service
public class PrestamoDesarrolloAlemanBean 
        extends PrestamoDesarrolloBaseBean implements PrestamoDesarrolloBean {

    @Override
    public List<PrestamoCuota> calcular(PrestamoEntity prestamo) {
        // TODO Auto-generated method stub
        return null;
    }

}
