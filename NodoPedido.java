package com.proyectofinal.jrmg.service;

import com.proyectofinal.jrmg.model.Usuario;
import com.proyectofinal.jrmg.structures.lista.ListaEnlazadaUsuario;
import com.proyectofinal.jrmg.util.CsvParser;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    private final ListaEnlazadaUsuario listaUsuarios;

    public UsuarioService() {
        this.listaUsuarios = new ListaEnlazadaUsuario();
        // Insertar un usuario por defecto para permitir el primer inicio de sesión
        this.listaUsuarios.insertar(Usuario.builder()
                .id(1)
                .nombre("Admin")
                .apellidos("MG")
                .contrasena("123")
                .build());
    }

    public ListaEnlazadaUsuario getListaUsuarios() {
        return listaUsuarios;
    }

    public synchronized boolean crearUsuario(Usuario usuario) {
        if (listaUsuarios.buscarPorId(usuario.getId()) != null) {
            return false; // ID ya existe
        }
        listaUsuarios.insertar(usuario);
        return true;
    }

    public synchronized Usuario buscarPorId(int id) {
        return listaUsuarios.buscarPorId(id);
    }

    public synchronized List<Usuario> obtenerTodos() {
        return listaUsuarios.obtenerTodos();
    }

    public synchronized boolean modificarUsuario(Usuario usuario) {
        return listaUsuarios.modificar(usuario);
    }

    public synchronized boolean eliminarUsuario(int id) {
        return listaUsuarios.eliminar(id);
    }

    public synchronized int cargarUsuariosCsv(String contenidoCsv, List<String> errores) {
        List<Usuario> usuariosNuevos = CsvParser.parseUsuarios(contenidoCsv, errores);
        int insertados = 0;
        for (Usuario u : usuariosNuevos) {
            if (crearUsuario(u)) {
                insertados++;
            } else {
                errores.add("ID " + u.getId() + " ya existe en el sistema.");
            }
        }
        return insertados;
    }
}
