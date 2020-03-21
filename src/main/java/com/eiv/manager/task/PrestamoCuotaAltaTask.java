package com.eiv.manager.task;

import com.eiv.entities.PrestamoCuotaEntity;
import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;
import com.eiv.repository.PrestamoCuotaRepository;

public class PrestamoCuotaAltaTask implements PrestamoTask<PrestamoCuotaEntity> {

    private PrestamoCuotaRepository prestamoCuotaService;
    private PrestamoEntity prestamoEntity;
    private PrestamoCuota prestamoCuota;

    public static PrestamoCuotaAltaTask newInstance() {
        return new PrestamoCuotaAltaTask();
    }
       
    public PrestamoCuotaAltaTask setPrestamoCuotaService(
            PrestamoCuotaRepository prestamoCuotaService) {
        this.prestamoCuotaService = prestamoCuotaService;
        return this;
    }

    public PrestamoCuotaAltaTask setPrestamoEntity(PrestamoEntity prestamoEntity) {
        this.prestamoEntity = prestamoEntity;
        return this;
    }

    public PrestamoCuotaAltaTask setPrestamoCuota(PrestamoCuota prestamoCuota) {
        this.prestamoCuota = prestamoCuota;
        return this;
    }

    @Override
    public PrestamoCuotaEntity execute() {
        return prestamoCuotaService.nueva(prestamoEntity, prestamoCuota);
    }
}
