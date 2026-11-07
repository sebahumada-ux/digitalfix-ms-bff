package cl.duoc.digitalfix.bff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CrearWorkOrderRequest(
        @NotNull(message = "El cliente es obligatorio")
        @Positive(message = "El ID del cliente debe ser positivo")
        Long clienteId,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
        String descripcion,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 255, message = "La dirección no puede superar 255 caracteres")
        String direccion,

        @Positive(message = "El ID del técnico debe ser positivo")
        Long tecnicoId
) {
}
