package com.proyectofinal.jrmg.structures.lista;

import com.proyectofinal.jrmg.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ListaEnlazadaUsuario {
    private NodoUsuario cabeza;
    private int tamanio;

    public ListaEnlazadaUsuario() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public synchronized void insertar(Usuario usuario) {
        NodoUsuario nuevo = new NodoUsuario(usuario);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public synchronized Usuario buscarPorId(int id) {
        NodoUsuario temp = cabeza;
        while (temp != null) {
            if (temp.getDato().getId() == id) {
                return temp.getDato();
            }
            temp = temp.getSiguiente();
        }
        return null;
    }

    public synchronized boolean modificar(Usuario usuarioModificado) {
        NodoUsuario temp = cabeza;
        while (temp != null) {
            if (temp.getDato().getId() == usuarioModificado.getId()) {
                temp.getDato().setNombre(usuarioModificado.getNombre());
                temp.getDato().setApellidos(usuarioModificado.getApellidos());
                if (usuarioModificado.getContrasena() != null && !usuarioModificado.getContrasena().isEmpty()) {
                    temp.getDato().setContrasena(usuarioModificado.getContrasena());
                }
                return true;
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    public synchronized boolean eliminar(int id) {
        if (cabeza == null) return false;

        if (cabeza.getDato().getId() == id) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return true;
        }

        NodoUsuario prev = cabeza;
        NodoUsuario curr = cabeza.getSiguiente();
        while (curr != null) {
            if (curr.getDato().getId() == id) {
                prev.setSiguiente(curr.getSiguiente());
                tamanio--;
                return true;
            }
            prev = curr;
            curr = curr.getSiguiente();
        }
        return false;
    }

    public synchronized int tamanio() {
        return tamanio;
    }

    public synchronized List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        NodoUsuario temp = cabeza;
        while (temp != null) {
            usuarios.add(temp.getDato());
            temp = temp.getSiguiente();
        }
        return usuarios;
    }

    // Para uso interno del generador DOT
    public synchronized NodoUsuario getCabeza() {
        return cabeza;
    }
}
