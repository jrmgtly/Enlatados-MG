package com.proyectofinal.jrmg.structures.avl;

import com.proyectofinal.jrmg.model.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodoAVL {
    private Cliente dato;
    private String llave; // CUI/DPI
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;

    public NodoAVL(Cliente dato) {
        this.dato = dato;
        this.llave = dato.getCui();
        this.izquierdo = null;
        this.derecho = null;
        this.altura = 1;
    }
}
