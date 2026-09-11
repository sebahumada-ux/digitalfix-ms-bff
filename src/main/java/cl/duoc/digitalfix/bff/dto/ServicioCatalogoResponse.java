package cl.duoc.digitalfix.bff.dto;

import java.math.BigDecimal;

public record ServicioCatalogoResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal tarifa,
        Integer stock,
        Boolean activo
) {
}