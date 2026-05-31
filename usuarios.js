package com.proyectofinal.jrmg.structures.pila;

import com.proyectofinal.jrmg.model.Caja;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodoPilaCaja {
    private Caja dato;
    private NodoPilaCaja siguiente;

    public NodoPilaCaja(Caja dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
