package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.service.AuthService;
import com.proyectofinal.jrmg.service.ReporteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final ReporteService reporteService;
    private final AuthService authService;

    public ReporteController(ReporteService reporteService, AuthService authService) {
        this.reporteService = reporteService;
        this.authService = authService;
    }

    private boolean noAutorizado(HttpSession session) {
        return !authService.estaAutenticado(session);
    }

    private ResponseEntity<String> respuestaNoAutorizado() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Debe iniciar sesión para visualizar los reportes.");
    }

    @GetMapping(value = "/usuarios", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reporteUsuarios(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotUsuarios());
    }

    @GetMapping(value = "/almacen", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reporteAlmacen(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotAlmacen());
    }

    @GetMapping(value = "/clientes", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reporteClientes(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotClientes());
    }

    @GetMapping(value = "/repartidores", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reporteRepartidores(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotRepartidores());
    }

    @GetMapping(value = "/vehiculos", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reporteVehiculos(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotVehiculos());
    }

    @GetMapping(value = "/pedidos", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reportePedidos(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotPedidos());
    }

    @GetMapping(value = "/estructura-general", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> reporteEstructuraGeneral(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();
        return ResponseEntity.ok(reporteService.generarDotEstructuraGeneral());
    }
}
