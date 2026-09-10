package cl.duoc.digitalfix.bff.dto;

import java.math.BigDecimal;

public record CrearServicioCatalogoRequest(
        String nombre,
        String descripcion,
        BigDecimal tarifa,
        Integer stock,
        Boolean activo
) {
}