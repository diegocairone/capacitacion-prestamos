package com.eiv.manager.task;

import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.interfaces.Prestamo;
import com.eiv.services.dal.PrestamoService;

public class PrestamoAltaTask implements PrestamoTask<PrestamoEntity> {

    private PrestamoService prestamoService;
    private Prestamo prestamo;
    private UsuarioEntity usuario;
    
    private PrestamoAltaTask() {
    }
    
    public static PrestamoAltaTask newInstance() {
        return new PrestamoAltaTask();
    }
        
    public PrestamoAltaTask setPrestamoService(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
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
        return prestamoService.nuevo(prestamo, usuario);
    }
}
