package com.proyectofinal.jrmg.controller;

import com.proyectofinal.jrmg.dto.response.ApiResponse;
import com.proyectofinal.jrmg.model.Usuario;
import com.proyectofinal.jrmg.service.AuthService;
import com.proyectofinal.jrmg.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthService authService;

    public UsuarioController(UsuarioService usuarioService, AuthService authService) {
        this.usuarioService = usuarioService;
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
    public ResponseEntity<ApiResponse> crear(@RequestBody Usuario usuario, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        if (usuarioService.crearUsuario(usuario)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Usuario creado exitosamente.")
                    .datos(usuario)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .exito(false)
                .mensaje("El ID " + usuario.getId() + " ya está registrado.")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listar(HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        List<Usuario> lista = usuarioService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.builder()
                .exito(true)
                .mensaje("Lista de usuarios recuperada.")
                .datos(lista)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> buscarPorId(@PathVariable int id, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Usuario encontrado.")
                    .datos(usuario)
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Usuario con ID " + id + " no encontrado.")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> modificar(@PathVariable int id, @RequestBody Usuario usuario, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        usuario.setId(id);
        if (usuarioService.modificarUsuario(usuario)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Usuario modificado exitosamente.")
                    .datos(usuarioService.buscarPorId(id))
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Usuario con ID " + id + " no encontrado.")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> eliminar(@PathVariable int id, HttpSession session) {
        if (noAutorizado(session)) return respuestaNoAutorizado();

        // Evitar que el admin activo se elimine a sí mismo
        Usuario activo = authService.obtenerUsuarioLogueado(session);
        if (activo != null && activo.getId() == id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                    .exito(false)
                    .mensaje("No puede eliminarse a sí mismo mientras su sesión esté activa.")
                    .build());
        }

        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.ok(ApiResponse.builder()
                    .exito(true)
                    .mensaje("Usuario eliminado exitosamente.")
                    .build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                .exito(false)
                .mensaje("Usuario con ID " + id + " no encontrado.")
                .build());
    }
}
