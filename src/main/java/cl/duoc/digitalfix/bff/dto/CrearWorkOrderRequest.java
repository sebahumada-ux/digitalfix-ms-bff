package cl.duoc.digitalfix.bff.dto;

public record CrearWorkOrderRequest(
        Long clienteId,
        String descripcion,
        String direccion,
        Long tecnicoId
) {
}