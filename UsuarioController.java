package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.request.LoginRequest;
import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Usuario;
import com.proyectofinal.jrmg.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        Usuario usuario = authService.login(request.getId(), request.getContrasena(), session);
        if (usuario != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Inicio de sesión exitoso.")
                    .datos(usuario)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                .exito(false)
                .mensaje("ID o contraseña incorrectos.")
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Sesión cerrada exitosamente.")
                .build());
    }

    @GetMapping("/perfil")
    public ResponseEntity<ApiResponse> perfil(HttpSession session) {
        Usuario usuario = authService.obtenerUsuarioLogueado(session);
        if (usuario != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Perfil del usuario recuperado.")
                    .datos(usuario)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                .exito(false)
                .mensaje("No hay una sesión activa.")
                .build());
    }
}
