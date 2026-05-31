package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.request.PedidoRequest;
import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Caja;
import com.proyectofinal.jrmg.model.Pedido;
import com.proyectofinal.jrmg.service.AuthService;
import com.proyectofinal.jrmg.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;
    private final AuthService authService;

    public PedidoController(PedidoService pedidoService, AuthService authService) {
        this.pedidoService = pedidoService;
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
    public ResponseEntity<ApiResponse> crear(@RequestBody PedidoRequest request, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        try {
            Pedido pedido = pedidoService.crearPedido(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Pedido creado exitosamente.")
                    .datos(pedido)
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje(e.getMessage())
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listar(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Lista de pedidos recuperada.")
                .datos(pedidos)
                .build());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<ApiResponse> buscarPorNumero(@PathVariable int numero, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        Pedido pedido = pedidoService.buscarPorNumero(numero);
        if (pedido != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Pedido encontrado.")
                    .datos(pedido)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Pedido número " + numero + " no encontrado.")
                .build());
    }

    @GetMapping("/{numero}/cajas")
    public ResponseEntity<ApiResponse> obtenerCajasDelPedido(@PathVariable int numero, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        Pedido pedido = pedidoService.buscarPorNumero(numero);
        if (pedido != null) {
            List<Caja> listaCajas = pedido.getCajas().obtenerTodas();
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Cajas del pedido número " + numero + " recuperadas.")
                    .datos(listaCajas)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Pedido número " + numero + " no encontrado.")
                .build());
    }

    @PutMapping("/{numero}/completar")
    public ResponseEntity<ApiResponse> completar(@PathVariable int numero, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        try {
            Pedido pedido = pedidoService.completarPedido(numero);
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Pedido número " + numero + " completado exitosamente. Los recursos han retornado a sus colas.")
                    .datos(pedido)
                    .build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje(e.getMessage())
                    .build());
        }
    }
}
