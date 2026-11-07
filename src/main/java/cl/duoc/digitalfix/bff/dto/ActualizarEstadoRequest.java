package cl.duoc.digitalfix.bff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarEstadoRequest(
        @NotBlank(message = "El estado es obligatorio")
        String estado,

        @Size(max = 1000, message = "La observación no puede superar 1000 caracteres")
        String observacionTecnica
) {
}
