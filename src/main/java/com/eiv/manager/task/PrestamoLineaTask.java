package com.eiv.manager.task;

import com.eiv.entities.LineaEntity;
import com.eiv.repository.LineaRepository;
import com.eiv.utiles.ExceptionUtils;

public class PrestamoLineaTask implements PrestamoTask<LineaEntity> {

    private LineaRepository lineaRepository;
    private Long lineaId;
    
    private PrestamoLineaTask() {
    }
    
    public PrestamoLineaTask setLineaRepository(LineaRepository lineaRepository) {
        this.lineaRepository = lineaRepository;
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

        LineaEntity lineaEntity = lineaRepository.findById(lineaId)
                .orElseThrow(ExceptionUtils.notFoundExceptionSupplier(
                        "NO EXISTE UNA LINEA CON ID %s", lineaId));

        return lineaEntity;
    }
}
