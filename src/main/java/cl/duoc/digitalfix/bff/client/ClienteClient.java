package cl.duoc.digitalfix.bff.client;

import cl.duoc.digitalfix.bff.dto.ClienteResponse;
import cl.duoc.digitalfix.bff.dto.CrearClienteRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class ClienteClient {

    private final RestClient restClient;

    public ClienteClient(
            @Value("${services.workorders.url}") String workordersUrl) {
        this.restClient = RestClient.create(workordersUrl);
    }

    public List<ClienteResponse> buscar(String nombre) {
        ClienteResponse[] resultado = restClient.get()
                .uri(builder -> {
                    builder.path("/api/clientes");
                    if (nombre != null && !nombre.isBlank()) {
                        builder.queryParam("nombre", nombre.trim());
                    }
                    return builder.build();
                })
                .retrieve()
                .body(ClienteResponse[].class);

        return resultado == null ? List.of() : Arrays.asList(resultado);
    }

    public ClienteResponse crear(CrearClienteRequest request) {
        return restClient.post()
                .uri("/api/clientes")
                .body(request)
                .retrieve()
                .body(ClienteResponse.class);
    }
}
