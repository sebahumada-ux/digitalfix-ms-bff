package cl.duoc.digitalfix.bff.dto;

import java.math.BigDecimal;

public record ActualizarServicioCatalogoRequest(
        BigDecimal tarifa,
        Integer stock
) {
}