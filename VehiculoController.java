package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.dto.response.CargaResultado;
import com.proyectofinal.jrmg.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/carga")
public class CargaMasivaController {
    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final ClienteService clienteService;
    private final RepartidorService repartidorService;
    private final VehiculoService vehiculoService;

    public CargaMasivaController(AuthService authService,
                                 UsuarioService usuarioService,
                                 ClienteService clienteService,
                                 RepartidorService repartidorService,
                                 VehiculoService vehiculoService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        this.repartidorService = repartidorService;
        this.vehiculoService = vehiculoService;
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

    @PostMapping("/usuarios")
    public ResponseEntity<ApiResponse> cargarUsuarios(@RequestBody String contenido, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<String> errores = new ArrayList<>();
        int insertados = usuarioService.cargarUsuariosCsv(contenido, errores);
        
        CargaResultado res = CargaResultado.builder()
                .insertados(insertados)
                .errores(errores)
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Carga de usuarios procesada.")
                .datos(res)
                .build());
    }

    @PostMapping("/clientes")
    public ResponseEntity<ApiResponse> cargarClientes(@RequestBody String contenido, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<String> errores = new ArrayList<>();
        int insertados = clienteService.cargarClientesCsv(contenido, errores);

        CargaResultado res = CargaResultado.builder()
                .insertados(insertados)
                .errores(errores)
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Carga de clientes procesada.")
                .datos(res)
                .build());
    }

    @PostMapping("/repartidores")
    public ResponseEntity<ApiResponse> cargarRepartidores(@RequestBody String contenido, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<String> errores = new ArrayList<>();
        int insertados = repartidorService.cargarRepartidoresCsv(contenido, errores);

        CargaResultado res = CargaResultado.builder()
                .insertados(insertados)
                .errores(errores)
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Carga de repartidores procesada.")
                .datos(res)
                .build());
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<ApiResponse> cargarVehiculos(@RequestBody String contenido, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<String> errores = new ArrayList<>();
        int insertados = vehiculoService.cargarVehiculosCsv(contenido, errores);

        CargaResultado res = CargaResultado.builder()
                .insertados(insertados)
                .errores(errores)
                .build();

        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Carga de vehículos procesada.")
                .datos(res)
                .build());
    }
}
