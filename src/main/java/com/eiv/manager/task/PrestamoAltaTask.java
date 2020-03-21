package com.eiv.manager.task;

import com.eiv.entities.PrestamoEntity;
import com.eiv.entities.UsuarioEntity;
import com.eiv.interfaces.Prestamo;
import com.eiv.repository.PrestamoRepository;

public class PrestamoAltaTask implements PrestamoTask<PrestamoEntity> {

    private PrestamoRepository prestamoRepository;
    private Prestamo prestamo;
    private UsuarioEntity usuario;
    
    private PrestamoAltaTask() {
    }
    
    public static PrestamoAltaTask newInstance() {
        return new PrestamoAltaTask();
    }
        
    public PrestamoAltaTask setPrestamoRepository(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
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
        return prestamoRepository.nuevo(prestamo, usuario);
    }
}
