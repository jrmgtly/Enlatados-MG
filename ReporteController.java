package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Caja;
import com.proyectofinal.jrmg.service.AlmacenService;
import com.proyectofinal.jrmg.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/almacen")
public class AlmacenController {
    private final AlmacenService almacenService;
    private final AuthService authService;

    public AlmacenController(AlmacenService almacenService, AuthService authService) {
        this.almacenService = almacenService;
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

    @PostMapping("/cajas")
    public ResponseEntity<ApiResponse> agregarCajas(@RequestParam(defaultValue = "1") int cantidad, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (cantidad <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje("La cantidad debe ser mayor a cero.")
                    .build());
        }

        almacenService.agregarCajas(cantidad);
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Se agregaron " + cantidad + " cajas exitosamente.")
                .datos(almacenService.obtenerStock())
                .build());
    }

    @GetMapping("/cajas")
    public ResponseEntity<ApiResponse> obtenerCajas(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<Caja> lista = almacenService.obtenerTodas();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Cajas obtenidas de la pila.")
                .datos(lista)
                .build());
    }

    @GetMapping("/stock")
    public ResponseEntity<ApiResponse> obtenerStock(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        int stock = almacenService.obtenerStock();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Stock recuperado.")
                .datos(stock)
                .build());
    }
}
