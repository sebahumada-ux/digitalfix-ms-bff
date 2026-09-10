package cl.duoc.digitalfix.bff.client;

import cl.duoc.digitalfix.bff.dto.ActualizarServicioCatalogoRequest;
import cl.duoc.digitalfix.bff.dto.CrearServicioCatalogoRequest;
import cl.duoc.digitalfix.bff.dto.ServicioCatalogoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class CatalogClient {

    private final RestClient restClient;

    public CatalogClient(
            @Value("${services.catalog.url}") String catalogUrl) {

        this.restClient = RestClient.create(catalogUrl);
    }

    public List<ServicioCatalogoResponse> listar() {

        ServicioCatalogoResponse[] response = restClient
                .get()
                .uri("/api/catalog/services")
                .retrieve()
                .body(ServicioCatalogoResponse[].class);

        return response == null
                ? List.of()
                : Arrays.asList(response);
    }

    public ServicioCatalogoResponse buscarPorId(Long id) {

        return restClient
                .get()
                .uri("/api/catalog/services/{id}", id)
                .retrieve()
                .body(ServicioCatalogoResponse.class);
    }

    public ServicioCatalogoResponse crear(
            CrearServicioCatalogoRequest request) {

        return restClient
                .post()
                .uri("/api/catalog/services")
                .body(request)
                .retrieve()
                .body(ServicioCatalogoResponse.class);
    }

    public ServicioCatalogoResponse actualizar(
            Long id,
            ActualizarServicioCatalogoRequest request) {

        return restClient
                .put()
                .uri("/api/catalog/services/{id}", id)
                .body(request)
                .retrieve()
                .body(ServicioCatalogoResponse.class);
    }
}