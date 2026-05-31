package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Vehiculo;
import com.proyectofinal.jrmg.service.AuthService;
import com.proyectofinal.jrmg.service.VehiculoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoService vehiculoService;
    private final AuthService authService;

    public VehiculoController(VehiculoService vehiculoService, AuthService authService) {
        this.vehiculoService = vehiculoService;
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
    public ResponseEntity<ApiResponse> crear(@RequestBody Vehiculo vehiculo, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje("La placa es obligatoria.")
                    .build());
        }

        if (vehiculoService.crearVehiculo(vehiculo)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Vehículo registrado y encolado exitosamente.")
                    .datos(vehiculo)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .exito(false)
                .mensaje("El vehículo con placa " + vehiculo.getPlaca() + " ya existe.")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listar(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<Vehiculo> disponibles = vehiculoService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Lista de vehículos disponibles recuperada.")
                .datos(disponibles)
                .build());
    }

    @GetMapping("/{placa}")
    public ResponseEntity<ApiResponse> buscarPorPlaca(@PathVariable String placa, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        Vehiculo vehiculo = vehiculoService.buscarPorPlaca(placa);
        if (vehiculo != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Vehículo encontrado.")
                    .datos(vehiculo)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Vehículo con placa " + placa + " no encontrado en la cola de disponibles.")
                .build());
    }

    @PutMapping("/{placa}")
    public ResponseEntity<ApiResponse> modificar(@PathVariable String placa, @RequestBody Vehiculo vehiculo, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        vehiculo.setPlaca(placa);
        if (vehiculoService.modificarVehiculo(vehiculo)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Vehículo modificado exitosamente.")
                    .datos(vehiculoService.buscarPorPlaca(placa))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Vehículo con placa " + placa + " no encontrado o se encuentra ocupado en un pedido.")
                .build());
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<ApiResponse> eliminar(@PathVariable String placa, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (vehiculoService.eliminarVehiculo(placa)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Vehículo eliminado exitosamente.")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Vehículo con placa " + placa + " no encontrado o se encuentra ocupado en un pedido.")
                .build());
    }
}
