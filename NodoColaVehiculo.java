package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final String CLAVE_SESION_USUARIO = "usuario_autenticado";
    private final UsuarioService usuarioService;

    public AuthService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario login(int id, String contrasena, HttpSession session) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            session.setAttribute(CLAVE_SESION_USUARIO, usuario);
            return usuario;
        }
        return null;
    }

    public void logout(HttpSession session) {
        session.removeAttribute(CLAVE_SESION_USUARIO);
        session.invalidate();
    }

    public Usuario obtenerUsuarioLogueado(HttpSession session) {
        return (Usuario) session.getAttribute(CLAVE_SESION_USUARIO);
    }

    public boolean estaAutenticado(HttpSession session) {
        return obtenerUsuarioLogueado(session) != null;
    }
}
