package cl.duoc.digitalfix.bff.controller;

import cl.duoc.digitalfix.bff.client.WorkordersClient;
import cl.duoc.digitalfix.bff.dto.ActualizarEstadoRequest;
import cl.duoc.digitalfix.bff.dto.CrearWorkOrderRequest;
import cl.duoc.digitalfix.bff.dto.WorkOrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff/workorders")
public class BffWorkordersController {

    private final WorkordersClient workordersClient;

    public BffWorkordersController(WorkordersClient workordersClient) {
        this.workordersClient = workordersClient;
    }

    @GetMapping
    public ResponseEntity<List<WorkOrderResponse>> listar() {
        return ResponseEntity.ok(workordersClient.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                workordersClient.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<WorkOrderResponse> crear(
            @Valid @RequestBody CrearWorkOrderRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workordersClient.crear(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<WorkOrderResponse> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEstadoRequest request) {

        return ResponseEntity.ok(
                workordersClient.actualizarEstado(id, request)
        );
    }
}