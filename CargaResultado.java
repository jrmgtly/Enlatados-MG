package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Repartidor;
import com.proyectofinal.jrmg.service.AuthService;
import com.proyectofinal.jrmg.service.RepartidorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {
    private final RepartidorService repartidorService;
    private final AuthService authService;

    public RepartidorController(RepartidorService repartidorService, AuthService authService) {
        this.repartidorService = repartidorService;
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
    public ResponseEntity<ApiResponse> crear(@RequestBody Repartidor repartidor, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (repartidor.getCui() == null || repartidor.getCui().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje("El CUI/DPI es obligatorio.")
                    .build());
        }

        if (repartidorService.crearRepartidor(repartidor)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Repartidor registrado y encolado exitosamente.")
                    .datos(repartidor)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .exito(false)
                .mensaje("El repartidor con CUI/DPI " + repartidor.getCui() + " ya existe.")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listar(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<Repartidor> disponibles = repartidorService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Lista de repartidores disponibles recuperada.")
                .datos(disponibles)
                .build());
    }

    @GetMapping("/{cui}")
    public ResponseEntity<ApiResponse> buscarPorCui(@PathVariable String cui, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        Repartidor repartidor = repartidorService.buscarPorCui(cui);
        if (repartidor != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Repartidor encontrado.")
                    .datos(repartidor)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Repartidor con CUI/DPI " + cui + " no encontrado en la cola de disponibles.")
                .build());
    }

    @PutMapping("/{cui}")
    public ResponseEntity<ApiResponse> modificar(@PathVariable String cui, @RequestBody Repartidor repartidor, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        repartidor.setCui(cui);
        if (repartidorService.modificarRepartidor(repartidor)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Repartidor modificado exitosamente.")
                    .datos(repartidorService.buscarPorCui(cui))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Repartidor con CUI/DPI " + cui + " no encontrado o se encuentra ocupado en un pedido.")
                .build());
    }

    @DeleteMapping("/{cui}")
    public ResponseEntity<ApiResponse> eliminar(@PathVariable String cui, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (repartidorService.eliminarRepartidor(cui)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Repartidor eliminado exitosamente.")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Repartidor con CUI/DPI " + cui + " no encontrado o se encuentra ocupado en un pedido.")
                .build());
    }
}
