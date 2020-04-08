package com.eiv.manager.task;

import com.eiv.das.PrestamoCuotaDas;
import com.eiv.entities.PrestamoCuotaEntity;
import com.eiv.entities.PrestamoEntity;
import com.eiv.interfaces.PrestamoCuota;

public class PrestamoCuotaAltaTask implements PrestamoTask<PrestamoCuotaEntity> {

    private PrestamoCuotaDas prestamoCuotaDas;
    private PrestamoEntity prestamoEntity;
    private PrestamoCuota prestamoCuota;

    public static PrestamoCuotaAltaTask newInstance() {
        return new PrestamoCuotaAltaTask();
    }
       
    public PrestamoCuotaAltaTask setPrestamoCuotaDas(
            PrestamoCuotaDas prestamoCuotaDas) {
        this.prestamoCuotaDas = prestamoCuotaDas;
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
        return prestamoCuotaDas.nueva(prestamoEntity, prestamoCuota);
    }
}
