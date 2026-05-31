package com.proyectofinal.jrmg.structures.lista;

import com.proyectofinal.jrmg.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodoUsuario {
    private Usuario dato;
    private NodoUsuario siguiente;

    public NodoUsuario(Usuario dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
