package cl.duoc.digitalfix.bff.controller;

import cl.duoc.digitalfix.bff.client.ClienteClient;
import cl.duoc.digitalfix.bff.dto.ClienteResponse;
import cl.duoc.digitalfix.bff.dto.CrearClienteRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff/clientes")
public class BffClientesController {

    private final ClienteClient client;

    public BffClientesController(ClienteClient client) {
        this.client = client;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> buscar(
            @RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(client.buscar(nombre));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(
            @Valid @RequestBody CrearClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(client.crear(request));
    }
}
