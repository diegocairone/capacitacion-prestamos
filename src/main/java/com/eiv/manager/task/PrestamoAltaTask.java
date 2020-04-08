package com.eiv.manager.task;

import com.eiv.das.PrestamoDas;
import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.interfaces.Prestamo;

public class PrestamoAltaTask implements PrestamoTask<PrestamoEntity> {

    private PrestamoDas prestamoDas;
    private Prestamo prestamo;
    private UsuarioEntity usuario;
    
    private PrestamoAltaTask() {
    }
    
    public static PrestamoAltaTask newInstance() {
        return new PrestamoAltaTask();
    }
        
    public PrestamoAltaTask setPrestamoDas(PrestamoDas prestamoDas) {
        this.prestamoDas = prestamoDas;
        return this;
    }

    public PrestamoAltaTask setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
        return this;
    }

    public PrestamoAltaTask setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
        return this;
    }

    @Override
    public PrestamoEntity execute() {
        return prestamoDas.nuevo(prestamo, usuario);
    }
}
