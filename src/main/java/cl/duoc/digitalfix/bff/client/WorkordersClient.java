package cl.duoc.digitalfix.bff.client;

import cl.duoc.digitalfix.bff.dto.ActualizarEstadoRequest;
import cl.duoc.digitalfix.bff.dto.CrearWorkOrderRequest;
import cl.duoc.digitalfix.bff.dto.WorkOrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class WorkordersClient {

    private final RestClient restClient;

    public WorkordersClient(
            @Value("${services.workorders.url}") String workordersUrl) {

        this.restClient = RestClient.create(workordersUrl);
    }

    public List<WorkOrderResponse> listar() {

        WorkOrderResponse[] response = restClient
                .get()
                .uri("/api/workorders")
                .retrieve()
                .body(WorkOrderResponse[].class);

        return response == null
                ? List.of()
                : Arrays.asList(response);
    }

    public WorkOrderResponse buscarPorId(Long id) {

        return restClient
                .get()
                .uri("/api/workorders/{id}", id)
                .retrieve()
                .body(WorkOrderResponse.class);
    }

    public WorkOrderResponse crear(CrearWorkOrderRequest request) {

        return restClient
                .post()
                .uri("/api/workorders")
                .body(request)
                .retrieve()
                .body(WorkOrderResponse.class);
    }

    public WorkOrderResponse actualizarEstado(
            Long id,
            ActualizarEstadoRequest request) {

        return restClient
                .put()
                .uri("/api/workorders/{id}/status", id)
                .body(request)
                .retrieve()
                .body(WorkOrderResponse.class);
    }
}