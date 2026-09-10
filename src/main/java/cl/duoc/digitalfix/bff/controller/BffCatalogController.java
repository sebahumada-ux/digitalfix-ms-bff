package cl.duoc.digitalfix.bff.controller;

import cl.duoc.digitalfix.bff.client.CatalogClient;
import cl.duoc.digitalfix.bff.dto.ActualizarServicioCatalogoRequest;
import cl.duoc.digitalfix.bff.dto.CrearServicioCatalogoRequest;
import cl.duoc.digitalfix.bff.dto.ServicioCatalogoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff/catalog/services")
public class BffCatalogController {

    private final CatalogClient catalogClient;

    public BffCatalogController(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }

    @GetMapping
    public ResponseEntity<List<ServicioCatalogoResponse>> listar() {

        return ResponseEntity.ok(
                catalogClient.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioCatalogoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                catalogClient.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ServicioCatalogoResponse> crear(
            @RequestBody CrearServicioCatalogoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(catalogClient.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioCatalogoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarServicioCatalogoRequest request) {

        return ResponseEntity.ok(
                catalogClient.actualizar(id, request)
        );
    }
}