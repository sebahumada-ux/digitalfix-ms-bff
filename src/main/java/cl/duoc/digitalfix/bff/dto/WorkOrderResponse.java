package cl.duoc.digitalfix.bff.dto;

import java.time.LocalDateTime;

public record WorkOrderResponse(
        Long id,
        Long clienteId,
        String descripcion,
        String direccion,
        Long tecnicoId,
        String estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {
}