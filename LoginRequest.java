package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Cliente;
import com.proyectofinal.jrmg.service.AuthService;
import com.proyectofinal.jrmg.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final AuthService authService;

    public ClienteController(ClienteService clienteService, AuthService authService) {
        this.clienteService = clienteService;
        this.authService = authService;
    }

    private boolean noAutorizado(HttpSession session) {
        return !authService.estaAutenticado(session);
    }

    private ResponseEntity<ApiResponse> respuestaNoAutorizado() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Debe iniciar sesión para realizar esta acción.")
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> crear(@RequestBody Cliente cliente, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (cliente.getCui() == null || cliente.getCui().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje("El CUI/DPI es obligatorio.")
                    .build());
        }

        if (clienteService.crearCliente(cliente)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Cliente registrado exitosamente.")
                    .datos(cliente)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .exito(false)
                .mensaje("El CUI/DPI " + cliente.getCui() + " ya está registrado.")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listar(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<Cliente> clientes = clienteService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Lista de clientes recuperada.")
                .datos(clientes)
                .build());
    }

    @GetMapping("/{cui}")
    public ResponseEntity<ApiResponse> buscarPorCui(@PathVariable String cui, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        Cliente cliente = clienteService.buscarPorCui(cui);
        if (cliente != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Cliente encontrado.")
                    .datos(cliente)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Cliente con CUI/DPI " + cui + " no encontrado.")
                .build());
    }

    @PutMapping("/{cui}")
    public ResponseEntity<ApiResponse> modificar(@PathVariable String cui, @RequestBody Cliente cliente, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        cliente.setCui(cui);
        if (clienteService.modificarCliente(cliente)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Cliente modificado exitosamente.")
                    .datos(clienteService.buscarPorCui(cui))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Cliente con CUI/DPI " + cui + " no encontrado.")
                .build());
    }

    @DeleteMapping("/{cui}")
    public ResponseEntity<ApiResponse> eliminar(@PathVariable String cui, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (clienteService.eliminarCliente(cui)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Cliente eliminado exitosamente del árbol AVL.")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Cliente con CUI/DPI " + cui + " no encontrado.")
                .build());
    }
}
