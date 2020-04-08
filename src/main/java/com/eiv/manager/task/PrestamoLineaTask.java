package com.eiv.manager.task;

import com.eiv.das.LineaDas;
import com.eiv.entities.LineaEntity;
import com.eiv.utiles.ExceptionUtils;

public class PrestamoLineaTask implements PrestamoTask<LineaEntity> {

    private LineaDas lineaDas;
    private Long lineaId;
    
    private PrestamoLineaTask() {
    }
    
    public PrestamoLineaTask setLineaDas(LineaDas lineaDas) {
        this.lineaDas = lineaDas;
        return this;
    }

    public PrestamoLineaTask setLineaId(Long lineaId) {
        this.lineaId = lineaId;
        return this;
    }

    public static PrestamoLineaTask newInstance() {
        return new PrestamoLineaTask();
    }
    
    @Override
    public LineaEntity execute() {

        LineaEntity lineaEntity = lineaDas.findById(lineaId)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UNA LINEA CON ID %s", lineaId));

        return lineaEntity;
    }
}
